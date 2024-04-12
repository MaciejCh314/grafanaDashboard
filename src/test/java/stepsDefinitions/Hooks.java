package stepsDefinitions;

import Utils.ResultSender;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import org.influxdb.dto.Point;
import io.cucumber.plugin.event.Step;
import runners.StepDetails;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Hooks {

    private long startTime;
    private long endTime;

    @BeforeStep
    public void beforeStep() {
        startTime = System.currentTimeMillis();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        System.out.println(StepDetails.stepName);
        endTime = System.currentTimeMillis();

        postStepMethodStatus(scenario.getStatus().name());
        if (scenario.getStatus() == Status.FAILED)
            postError();
    }

    private void postStepMethodStatus(String status) {
        Point point = Point.measurement("testmethod")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("testclass", StepDetails.methodName)
                .addField("name", StepDetails.stepName)
                .tag("result", status)
                .addField("duration", (endTime - startTime))
                .addField("step", formatStepInfo(StepDetails.keyWord, StepDetails.stepName, StepDetails.methodName, status, (endTime - startTime)))
                .build();
        ResultSender.send(point);
        Point step = Point.measurement("steps")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("step", formatStepInfo(StepDetails.keyWord, StepDetails.stepName, StepDetails.methodName, status, (endTime - startTime)))
                .build();
        ResultSender.send(step);
    }

    private void postError() {
        Point error = Point.measurement("errors")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("error", StepDetails.error.toString() + "\n" + Arrays.stream(StepDetails.error.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.joining("\n")))
                .build();
        ResultSender.send(error);
    }

    public static String formatStepInfo(String keyWord, String stepName, String methodName, String result, long duration) {
        return keyWord + " " + stepName + " (" + methodName + ") " + result + " " + convertLongToTimeString(duration);
    }

    public static String convertLongToTimeString(long timeInMillis) {
        long hours = timeInMillis / 3600000;
        long minutes = (timeInMillis % 3600000) / 60000;
        long seconds = ((timeInMillis % 3600000) % 60000) / 1000;
        long milliseconds = ((timeInMillis % 3600000) % 60000) % 1000;

        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }
}

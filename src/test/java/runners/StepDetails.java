package runners;

import io.cucumber.java.Scenario;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

public class StepDetails implements ConcurrentEventListener {

    public static String scenarioName;
    public static String keyWord;
    public static String stepName;
    public static String methodName;
    public static Throwable error;

    public EventHandler<TestStepStarted> stepStartedEventHandler = this::handleTestStepStarted;

    public EventHandler<TestStepFinished> stepFinishedEventHandler = this::handleTestStepFinished;

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, stepStartedEventHandler);
        publisher.registerHandlerFor(TestStepFinished.class, stepFinishedEventHandler);
    }

    private void handleTestStepStarted(TestStepStarted event) {
        scenarioName = event.getTestCase().getName();
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep testStep = (PickleStepTestStep)event.getTestStep();
            keyWord = testStep.getStep().getKeyword();
            stepName = testStep.getStep().getText();
            methodName = testStep.getCodeLocation();
        }
    }

    private void handleTestStepFinished(TestStepFinished event) {
        error = event.getResult().getError();
    }
}

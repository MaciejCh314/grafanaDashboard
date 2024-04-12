package runners;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

public class StepDetails implements ConcurrentEventListener {

    public static String keyWord;
    public static String stepName;
    public static String methodName;

    public static Throwable error;

    public EventHandler<TestStepStarted> stepStartedEventHandler = new EventHandler<TestStepStarted>() {
        @Override
        public void receive(TestStepStarted event) {
            handleTestStepStarted(event);
        }
    };

    public EventHandler<TestStepFinished> stepFinishedEventHandler = new EventHandler<TestStepFinished>() {
        @Override
        public void receive(TestStepFinished event) {
            handleTestStepFinished(event);
        }
    };

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, stepStartedEventHandler);
        publisher.registerHandlerFor(TestStepFinished.class, stepFinishedEventHandler);
    }

    private void handleTestStepStarted(TestStepStarted event) {
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

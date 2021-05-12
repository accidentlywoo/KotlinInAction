package chapter4;

import org.jetbrains.annotations.NotNull;

public class ButtonJava implements View{
    @Override
    public State getCurrentState() {
        return new ButtonState();
    }

    @Override
    public void restoreState(@NotNull State state) {
        View.super.restoreState(state);
    }

    public class ButtonState implements State {
        //
    }
}

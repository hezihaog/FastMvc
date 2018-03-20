package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import oms.mmc.lifecycle.dispatch.base.LifecycleFragment;

/**
 * 保存状态Fragment
 */
public class StatedFragment extends LifecycleFragment implements IInstanceState {
    private Bundle savedState;

    public StatedFragment() {
        super();
        if (getArguments() == null) {
            setArguments(new Bundle());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!restoreStateFromArguments()) {
            onFirstTimeLaunched();
        }
    }

    protected void onFirstTimeLaunched() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveStateToArguments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveStateToArguments();
    }

    private void saveStateToArguments() {
        if (getView() != null) {
            savedState = saveState();
        }
        if (savedState != null) {
            Bundle bundle = getArguments();
            bundle.putBundle("internalSavedViewState", savedState);
        }
    }

    private boolean restoreStateFromArguments() {
        Bundle bundle = getArguments();
        savedState = bundle.getBundle("internalSavedViewState");
        if (savedState != null) {
            restoreState();
            return true;
        }
        return false;
    }

    private Bundle saveState() {
        Bundle state = new Bundle();
        onSaveState(state);
        return state;
    }

    private void restoreState() {
        if (savedState != null) {
            onRestoreState(savedState);
        }
    }

    @Override
    public void onSaveState(Bundle stateBundle) {

    }

    @Override
    public void onRestoreState(Bundle stateBundle) {

    }
}
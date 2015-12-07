package com.example.manoel.eventbustest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.util.AsyncExecutor;
import de.greenrobot.event.util.ThrowableFailureEvent;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.txvConnectionStatus)
    TextView txvConnectionStatus;

    @Bind(R.id.txvLogin)
    TextView txvLogin;

    @Bind(R.id.btnLogin)
    Button btnLogin;

    EventBus bus = EventBus.getDefault();

    // flag to validate login
    boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // Register as a subscriber
        bus.register(this);

//        You may change the order of event delivery by providing a priority to the subscriber during registration.
//        int priority = 1;
//        EventBus.getDefault().register(this, priority);

//        Let's say, a sticky event was posted some time ago.
//        After that, a new Activity gets started. During registration using registerSticky,
//        it will immediately get the previously posted sticky event:
//        EventBus.getDefault().registerSticky(this);

//        You may also get the last sticky event of a certain type with:
//        EventBus.getDefault().getStickyEvent(Class<?> eventType)
    }

    @Override
    protected void onDestroy() {
        // Unregister
        bus.unregister(this);
        super.onDestroy();
    }

    // Called in the same thread (default)
    public void onEvent(NetworkEvent event){
        txvConnectionStatus.setText("Connected " + event.getData());

//        You may cancel the event delivery process by calling cancelEventDelivery(Object event)
//        from a subscriber's event handling method. Any further event delivery will be cancelled:
//        subsequent subscribers won't receive the event.
//        EventBus.getDefault().cancelEventDelivery(event);
    }

//    // Called in Android UI's main thread
//    public void onEventMainThread(MessageEvent event) {
//        textField.setText(event.message);
//    }

//    // Called in the background thread
//    public void onEventBackgroundThread(MessageEvent event){
//        saveToDisk(event.message);
//    }

//    // Called in a separate thread
//    public void onEventAsync(MessageEvent event){
//        backend.send(event.message);
//    }

    private void login() {
        if (!success) {
            success = true;
            throw new NullPointerException();
        } else {
            success = false;
        }
    }

    // Called in the same thread (default)
    public void onEventMainThread(LoggedInEvent event){
        txvLogin.setText("Login " + event.getData());

//        You may cancel the event delivery process by calling cancelEventDelivery(Object event)
//        from a subscriber's event handling method. Any further event delivery will be cancelled:
//        subsequent subscribers won't receive the event.
//        EventBus.getDefault().cancelEventDelivery(event);
    }

    public void onEventMainThread(ThrowableFailureEvent event) {
        txvLogin.setText("Login " + event.getThrowable().toString());
    }

    @OnClick(R.id.btnLogin)
    public void click() {

//        AsyncExecutor
//        Disclaimer: AsyncExecutor is a non-core utility class. It might save you some code with
//        error handling in background threads, but it's not a core EventBus class.

//        AsyncExecutor is like a thread pool, but with failure handling. Failures are thrown
//        exceptions, which get are wrapped inside an event, which is posted automatically by AsyncExecutor.

//        Usually, you call AsyncExecutor.create() to create an instance and keep it in
//        Application scope. To execute something, implement the RunnableEx interface and pass it to
//        the execute method of the AsyncExecutor. Unlike Runnable, RunnableEx may throw an Exception.

//        If the RunnableEx implementation throws an exception, it will be catched and wrapped into a
//        ThrowableFailureEvent, which will be posted.

        AsyncExecutor.create().execute(new AsyncExecutor.RunnableEx() {
            @Override
            public void run() throws Exception {
                login();
                EventBus.getDefault().postSticky(new LoggedInEvent("success"));
                // No need to catch Exception
            }
        });
    }

}

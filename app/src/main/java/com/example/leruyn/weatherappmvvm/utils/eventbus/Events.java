package com.example.leruyn.weatherappmvvm.utils.eventbus;


/**
 * Created by HoanVu on 10/10/2017.
 */

public class Events {


    public static class insertDataBase {
        boolean status;

        public insertDataBase(boolean status) {

            this.status = status;
        }

        public boolean getStatus() {
            return status;
        }

    }


}

package com.example.aidl_demo8;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MinhaAidlService extends Service {
    // Implementação do stub gerado a partir do arquivo AIDL
    public MinhaAidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
        // throw new UnsupportedOperationException("Not yet implemented");
    }
    // Implementação do stub gerado a partir do arquivo AIDL
    private final IMinhaAidlInterface.Stub binder = new IMinhaAidlInterface.Stub() {
        @Override
        public int somar(int a, int b) throws RemoteException {
            Log.d("MinhaAIDLService", "sum: "+ (b + a));
            return a + b;
        }
    };
}
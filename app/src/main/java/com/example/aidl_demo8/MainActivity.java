package com.example.aidl_demo8;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private IMinhaAidlInterface servico;
    private boolean vinculado = false;

    private final ServiceConnection conexao = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName nomeClasse, IBinder binder) {
            // Obter a instância da interface gerada
            servico = IMinhaAidlInterface.Stub.asInterface(binder);
            vinculado = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName nomeClasse) {
            vinculado = false;
        }
    };

    @Override
    protected void onCreate(Bundle estado) {
        super.onCreate(estado);
        setContentView(R.layout.activity_main);
        // Iniciar o bind ao serviço
        Intent intent = new Intent(this, MinhaAidlService.class);
        intent.setAction("com.seuprojeto.aidl.IMinhaAidlInterface");
        intent.setPackage(getPackageName());
        bindService(intent, conexao, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desvincular do serviço para evitar possíveis vazamentos de memória
        if (vinculado) {
            unbindService(conexao);
            vinculado = false;
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        // Exemplo de uso do serviço assim que a Activity fica em primeiro plano
        Log.d("AIDL_DEMO", "-------------- V: " + vinculado);
        Log.d("AIDL_DEMO", "-------------- S: " + servico);

        if (vinculado && servico != null) {
            try {
                int resultado = servico.somar(2, 3);
                Log.d("AIDL_DEMO", "Resultado da soma: " + resultado);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}

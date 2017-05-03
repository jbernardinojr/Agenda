package br.com.example.jbsjunior.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.example.jbsjunior.agenda.R;
import br.com.example.jbsjunior.agenda.dao.AlunoDAO;

/**
 * Created by junior on 01/05/17.
 */

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte [] pdu = (byte[]) pdus[0];

        String format = (String) intent.getSerializableExtra("format");
        SmsMessage sms;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sms = SmsMessage.createFromPdu(pdu, format);
        } else {
            sms = SmsMessage.createFromPdu(pdu);
        }

        String telephony = sms.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);
        if (dao.isAluno(telephony)) {
            Toast.makeText(context, "Sms recebido", Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }

        dao.close();
    }
}

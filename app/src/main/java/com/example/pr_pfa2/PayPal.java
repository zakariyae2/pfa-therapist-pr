package com.example.pr_pfa2;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class PayPal extends AppCompatActivity {
    TextView plan1,plan2,plan3;
    String clientId="AQrk3j30fMtrdwc3XYquCzd_K09eM8R2hjQGH1hVG6DWaeWihkvVj4W8V5XFLUiic0hmu_9OeHaSCm-L";
    int PAYPAL_REQUEST_CODE = 123;
    public static PayPalConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);

        plan1=(TextView)findViewById(R.id.pl1);
        plan2=(TextView)findViewById(R.id.pl2);
        plan3=(TextView)findViewById(R.id.pl3);

        configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(clientId);

        plan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment1();
            }
        });
        plan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment2();
            }
        });
        plan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment3();
            }
        });



    }
    private void getPayment1() {
        int amounts = 29;

        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amounts)),"USD","Code with Arvind",PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);

        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }
    private void getPayment2() {
        int amounts = 59;

        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amounts)),"USD","Code with Arvind",PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);

        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }
    private void getPayment3() {
        int amounts = 79;

        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amounts)),"USD","Code with Arvind",PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);

        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PAYPAL_REQUEST_CODE){
            PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            if(paymentConfirmation != null){


                try{
                    String paymentDetails = paymentConfirmation.toJSONObject().toString();
                    JSONObject object = new JSONObject(paymentDetails);
                }catch (JSONException e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            else if (requestCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this, "Invalid payment", Toast.LENGTH_SHORT).show();
        }
    }
}
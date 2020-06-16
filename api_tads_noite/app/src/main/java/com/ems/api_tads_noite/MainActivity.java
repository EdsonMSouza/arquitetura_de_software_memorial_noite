package com.ems.api_tads_noite;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    // Variável para encapsular os dados do objeto para o GSON (Push)
    String jsonInputString;

    // Variável para receber os dados enviados pelo servidor (Pull Request)
    StringBuilder response = new StringBuilder();

    // Vamos escrever uma class dentro da classe (Inner Class)
    public class UserService extends AsyncTask<Void, Void, List<User>> {
        // contrutor
        public UserService(User user) {
            Gson gson = new Gson();
            jsonInputString = gson.toJson(user);
            System.out.println("entrada" + jsonInputString);
        }

        protected List<User> doInBackground(Void... voids) {
            try {
                URL url = new URL("http://www.emsapi.esy.es/api_android/user.php");
                HttpURLConnection serv = (HttpURLConnection) url.openConnection();
                serv.setRequestMethod("POST");
                serv.setRequestProperty("Content-Type", "application/json; utf-8");
                serv.setRequestProperty("Accept", "application/json");
                serv.setConnectTimeout(5000); // 5 seg.
                serv.setReadTimeout(5000); // 5 seg.
                serv.setDoInput(true);
                serv.setDoOutput(true);

                // Enviar os dados para o servidor, executando a ESCRITA dos dados
                try (OutputStream outputStream = serv.getOutputStream()) {
                    // converte os dados JSON em bytes
                    byte[] input = jsonInputString.getBytes("utf-8");

                    // essa é a linha responsável por enviar os dados ao servidor
                    outputStream.write(input, 0, input.length);
                }

                // Leitura dos dados enviados pelo servidor
                try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(serv.getInputStream(), "utf-8"))) {
                    String responseLine = null;
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        response.append(responseLine.trim()); // trim() retira os espaços ante e depois da string
                    }
                    System.out.println("saida" + response.toString());
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            // "key":"value"
            // Preparar para a conversão de JSON para Objeto
            Type userType = new TypeToken<ArrayList<User>>() {
            }.getType();

            // Associa os dados tokenizados (JSON) com os atributos do objeto
            List<User> dados = new Gson().fromJson(response.toString(), userType);
            return dados;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView nome = findViewById(R.id.nome);

        User user = new User();
        user.setType("new");
        user.setName("Asseptgel de Souza");
        user.setUser("as");
        user.setPassword("as");

        try {

            ArrayList<User> dados = (ArrayList<User>) new UserService(user).execute().get();

            //System.out.println("Nome do usuário: " + dados.get(0).getName());
            //nome.setText(dados.get(0).getName());
            // se tiver mais de um objeto
            for(User u: dados){
                System.out.println(u.getName());
            }

        } catch (InterruptedException i) {
            System.out.println(i.getMessage());
        } catch (ExecutionException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
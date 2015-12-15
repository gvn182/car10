package simple.com.car10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.http.AndroidHttpClient;
import android.os.Environment;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;


public class DAL {
    String NAMESPACE = "http://apihomologacao.car10.com.br";
    String NAMESPACEHANDLER = "http://homologacao.car10.com.br";
    String SOAP_ACTION = "http://apihomologacao.car10.com.br/";
    String UserPass = "api.android@gmzsistemas.com.br:simple01@car10";
    String User = "api.android@gmzsistemas.com.br";
    String Pass = "simple01@car10";

    public String EfetuaLogin(String Email, String Senha) {
        URL url;
        HttpURLConnection conn;

        try {

            byte[] authEncBytes = Base64.encode(UserPass.getBytes(), Base64.DEFAULT);
            String authStringEnc = new String(authEncBytes);
            url = new URL(NAMESPACE + "/api/Login/PostAuthenticate");
            String param = "email=" + URLEncoder.encode(Email, "UTF-8") +
                    "&password=" + URLEncoder.encode(Senha, "UTF-8");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();

            String response = "";
            Scanner inStream = new Scanner(conn.getInputStream());

            while (inStream.hasNextLine())
                response += (inStream.nextLine());


            response = response.replace("\r\n", "");
            response = response.replace("\\", "");
            response = response.replace("rn", "");
            response = response.substring(1, response.length() - 1);

            return response;

        } catch (MalformedURLException ex) {
            return null;

        }
// and some more
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String AtualizaUsuario(String userID, String fullname, String cpf, String birthdate,
                                  String gender, String maritalStatus, String ddd1, String phone1, String ddd2, String phone2, String ddd3, String phone3) {
        URL url;
        HttpURLConnection conn;

        try {

            byte[] authEncBytes = Base64.encode(UserPass.getBytes(), Base64.DEFAULT);
            String authStringEnc = new String(authEncBytes);
            url = new URL(NAMESPACE + "/api/Usuario/PostValidateAndSaveUserData");
            String param = "userID=" + URLEncoder.encode(userID, "UTF-8") +
                    "&fullname=" + URLEncoder.encode(fullname, "UTF-8") +
                    "&cpf=" + URLEncoder.encode(cpf, "UTF-8") +
                    "&birthdate=" + URLEncoder.encode(birthdate, "UTF-8") +
                    "&gender=" + URLEncoder.encode(gender, "UTF-8") +
                    "&maritalStatus=" + URLEncoder.encode(maritalStatus, "UTF-8") +
                    "&phone1=" + URLEncoder.encode(ddd1 + phone1, "UTF-8");
            if (!phone2.equals("")) {
                param += "&phone2=" + URLEncoder.encode(ddd2 + phone2, "UTF-8");
            } else {
                param += "&phone2=" + URLEncoder.encode("null", "UTF-8");
            }
            if (!phone3.equals("")) {
                param += "&phone3=" + URLEncoder.encode(ddd3 + phone3, "UTF-8");
            } else {
                param += "&phone3=" + URLEncoder.encode("null", "UTF-8");
            }

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();

            String response = "";
            Scanner inStream = new Scanner(conn.getInputStream());

            while (inStream.hasNextLine())
                response += (inStream.nextLine());


            response = response.replace("\r\n", "");
            response = response.replace("\\", "");
            response = response.replace("rn", "");
            response = response.substring(1, response.length() - 1);

            return response;

        } catch (MalformedURLException ex) {
            return null;

        }
// and some more
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String GetUser(String ID) {


        try {
            byte[] authEncBytes = Base64.encode(UserPass.getBytes(), Base64.NO_WRAP);
            String authStringEnc = new String(authEncBytes);
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("userID", String.valueOf(ID)));
            String paramString = URLEncodedUtils.format(params, "UTF-8");
            String url = (NAMESPACE + "/api/Usuario/GetClientByUserID?" + paramString);
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpGet Get = new HttpGet(url);

            Get.addHeader("Authorization", "Basic " + authStringEnc);
            Get.addHeader("Content-Type", "application/json; charset=utf-8");

            HttpResponse httpResponse = httpclient.execute(Get);


            // receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();

            String result = "";
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
                result = result.replace("\r\n", "");
                result = result.replace("\\", "");
                result = result.replace("rn", "");
                result = result.substring(1, result.length() - 1);

            } else
                result = null;


            return result;

        } catch (MalformedURLException ex) {
            return null;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String PegaEndereco(String CEP) {


        try {

            String url = (NAMESPACEHANDLER + "/api/FindAddressByCEP/" + CEP);
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();

            String result = "";
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
                result = result.replace("\r\n", "");
                result = result.replace("\\", "");
                result = result.replace("rn", "");
                result = result.substring(1, result.length() - 1);

            } else
                result = null;


            return result;

        } catch (MalformedURLException ex) {
            return null;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String LatitudeLongitudeBy(Context context, String ZipCode) {
        final Geocoder geocoder = new Geocoder(context);
        final String zip = ZipCode;
        try {
            List<Address> addresses = geocoder.getFromLocationName(ZipCode, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Use the address as needed
                String message = String.format("%f;%f",
                        address.getLatitude(), address.getLongitude());

                return message;
            } else {
                return "0;0";
            }
        } catch (IOException e) {
            return "0;0";
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public String SalvaCotacao(String txtCEP, String lblEndereco, String txtNumero, String txtPlaca, String ddMarca, String ddModelo, String txtAnoModelo, String ddCor, String ddPintura, String ddSeguradora, boolean chkBlindado, boolean chkSeguro, EditText txtNomeCompleto, EditText txtDDD, EditText txtTelefone, EditText txtEmail, EditText txtSenha) {


        try {

            byte[] authEncBytes = Base64.encode(UserPass.getBytes(), Base64.DEFAULT);
            String authStringEnc = new String(authEncBytes);
            JSONObject JsonData = new JSONObject();
            String IP = "127.0.0.1";
            String Info = Devices.getDeviceName();
            String ResultSalvaFoto = SalvaImagens(IP, Info);
            if (!ResultSalvaFoto.equals("")) {

                String ID = ResultSalvaFoto;
                JsonData.put("ID", ID);
                JsonData.put("IP", IP);
                JsonData.put("info", Info);
                JsonData.put("CEP", txtCEP);
                JsonData.put("Endereco", lblEndereco.split(",")[0].trim());
                JsonData.put("Numero", txtNumero);
                JsonData.put("Bairro", lblEndereco.split(",")[1].trim());
                JsonData.put("Cidade", lblEndereco.split(",")[2].trim());
                JsonData.put("Estado", lblEndereco.split(",")[3].trim());
                JsonData.put("FabricanteID", ddMarca);
                JsonData.put("ModeloID", ddModelo);
                JsonData.put("Ano", txtAnoModelo);
                JsonData.put("Placa", txtPlaca);
                JsonData.put("VeiculoCorID", ddCor);
                JsonData.put("VeiculoPinturaID", ddPintura);
                JsonData.put("Blindado", chkBlindado ? "1" : "0");
                JsonData.put("SeguradoraID", ddSeguradora);
                JsonData.put("SeguradoraOutra", null);
                JsonData.put("NomeCompleto", txtNomeCompleto.getText().toString());
                JsonData.put("DDD", txtDDD.getText().toString());
                JsonData.put("Telefone", txtTelefone.getText().toString());
                JsonData.put("Email", txtEmail.getText().toString());
                JsonData.put("Senha", txtSenha.getText().toString());
                JsonData.put("Origem", "Android");

                Date d = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                JsonData.put("DataRegistro", format.format(d));
                URL url;
                HttpURLConnection conn;
                url = new URL(NAMESPACE + "/api/Cotacao/PostFinish");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
                conn.setRequestProperty("Content-type", "application/json");
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setFixedLengthStreamingMode(JsonData.toString().getBytes().length);
                PrintWriter out = new PrintWriter(conn.getOutputStream());
                out.print(JsonData.toString());
                out.close();

                String response = "";
                Scanner inStream = new Scanner(conn.getInputStream());

                while (inStream.hasNextLine())
                    response += (inStream.nextLine());


                return response;
            } else {
                return null;
            }


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String SalvaImagens(String IP, String Modelo) {

        String PredID = "";
        List<String> Partes = new ArrayList<String>();
        Partes.add("capo");
        Partes.add("dianteira");
        Partes.add("lateral_dianteira_direita");
        Partes.add("lateral_dianteira_esquerda");
        Partes.add("lateral_direita");
        Partes.add("lateral_esquerda");
        Partes.add("lateral_traseira_direita");
        Partes.add("lateral_traseira_esquerda");
        Partes.add("parabrisas_dianteiro");
        Partes.add("parabrisas_traseiro");
        Partes.add("roda_dianteira_direita");
        Partes.add("roda_dianteira_esquerda");
        Partes.add("roda_traseira_direita");
        Partes.add("roda_traseira_esquerda");
        Partes.add("teto");
        Partes.add("traseira");
        String result;
        boolean hasFile = false;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Car10";
        File dir = new File(file_path);
        for (int i = 0; i < Partes.size(); i++) {
            hasFile = false;

            try {

                List<NameValuePair> params = new LinkedList<NameValuePair>();
                params.add(new BasicNameValuePair("deviceinfo", Modelo));
                params.add(new BasicNameValuePair("IP", String.valueOf(IP)));
                params.add(new BasicNameValuePair("currentAreaID", Partes.get(i)));
                if (!PredID.equals("")) {
                    params.add(new BasicNameValuePair("preRegistroID", PredID));
                }
                String paramString = URLEncodedUtils.format(params, "UTF-8");


                String url = NAMESPACEHANDLER + "/reparos/_SetFotosOcorrencia.ashx?" + paramString;
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost(url);
                MultipartEntity entity = new MultipartEntity();

                for (int k = 1; k <= 3; k++) {
                    File file = new File(dir, "foto_" + Partes.get(i) + "_" + k + "_" + ".png");
                    if (file.exists()) {
                        hasFile = true;
                        entity.addPart("file", new FileBody(file));
                    }
                }

                File file = new File(dir, "video_" + Partes.get(i) + ".mp4");
                if (file.exists()) {
                    hasFile = true;
                    entity.addPart("file", new FileBody(file));
                }
                if (hasFile) {
                    postRequest.setEntity(entity);
                    HttpResponse response = httpClient.execute(postRequest);
                    result = EntityUtils.toString(response.getEntity());

                    result = result.replace("\r\n", "");
                    result = result.replace("\\", "");
                    result = result.replace("rn", "");

                    //  result = result.substring(1,result.length()-1);

                    final JSONObject object = new JSONObject(result);
                    PredID = object.getString("Data");
                }


            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                return "";

            } catch (IOException ex) {
                ex.printStackTrace();
                return "";
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }
        }
        return PredID;
    }

    public String GetOcorrencias(String ID) {


        try {
            byte[] authEncBytes = Base64.encode(UserPass.getBytes(), Base64.NO_WRAP);
            String authStringEnc = new String(authEncBytes);
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("userID", String.valueOf(ID)));
            String paramString = URLEncodedUtils.format(params, "UTF-8");
            String url = (NAMESPACE + "/api/Ocorrencia/GetByUserID?" + paramString);
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpGet Get = new HttpGet(url);

            Get.addHeader("Authorization", "Basic " + authStringEnc);
            Get.addHeader("Content-Type", "application/json; charset=utf-8");

            HttpResponse httpResponse = httpclient.execute(Get);


            // receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();

            String result = "";
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
                result = result.replace("\r\n", "");
                result = result.replace("\\", "");
                result = result.replace("rn", "");
                result = result.substring(1, result.length() - 1);

            } else
                result = null;


            return result;

        } catch (MalformedURLException ex) {
            return null;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String GetDetalheOcorrenciaBy(String ID) {


        try {
            byte[] authEncBytes = Base64.encode(UserPass.getBytes(), Base64.NO_WRAP);
            String authStringEnc = new String(authEncBytes);
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("occurrenceID", String.valueOf(ID)));
            String paramString = URLEncodedUtils.format(params, "UTF-8");
            String url = (NAMESPACE + "/api/Ocorrencia/GetByID?" + paramString);
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpGet Get = new HttpGet(url);

            Get.addHeader("Authorization", "Basic " + authStringEnc);
            Get.addHeader("Content-Type", "application/json; charset=utf-8");

            HttpResponse httpResponse = httpclient.execute(Get);


            // receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();

            String result = "";
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
                result = result.replace("\r\n", "");
                result = result.replace("\\", "");
                result = result.replace("rn", "");
                result = result.substring(1, result.length() - 1);

            } else
                result = null;


            return result;

        } catch (MalformedURLException ex) {
            return null;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String GetOcorrenciaBy(String ID) {


        try {
            byte[] authEncBytes = Base64.encode(UserPass.getBytes(), Base64.NO_WRAP);
            String authStringEnc = new String(authEncBytes);
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("occurrenceID", String.valueOf(ID)));
            String paramString = URLEncodedUtils.format(params, "UTF-8");
            String url = (NAMESPACE + "/api/Orcamento/GetByOccurrenceID?" + paramString);
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpGet Get = new HttpGet(url);

            Get.addHeader("Authorization", "Basic " + authStringEnc);
            Get.addHeader("Content-Type", "application/json; charset=utf-8");

            HttpResponse httpResponse = httpclient.execute(Get);


            // receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();

            String result = "";
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
                result = result.replace("\r\n", "");
                result = result.replace("\\", "");
                result = result.replace("rn", "");
                result = result.substring(1, result.length() - 1);

            } else
                result = null;


            return result;

        } catch (MalformedURLException ex) {
            return null;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String GetOficinaBy(String ID) {


        try {
            byte[] authEncBytes = Base64.encode(UserPass.getBytes(), Base64.NO_WRAP);
            String authStringEnc = new String(authEncBytes);
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("ID", String.valueOf(ID)));
            String paramString = URLEncodedUtils.format(params, "UTF-8");
            String url = (NAMESPACE + "/api/Empresa/GetByID?" + paramString);
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpGet Get = new HttpGet(url);

            Get.addHeader("Authorization", "Basic " + authStringEnc);
            Get.addHeader("Content-Type", "application/json; charset=utf-8");

            HttpResponse httpResponse = httpclient.execute(Get);


            // receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();

            String result = "";
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
                result = result.replace("\r\n", "");
                result = result.replace("\\", "");
                result = result.replace("rn", "");
                result = result.substring(1, result.length() - 1);

            } else
                result = null;


            return result;

        } catch (MalformedURLException ex) {
            return null;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String AprovaCotacao(String myID) {
        try {
            byte[] authEncBytes = Base64.encode(UserPass.getBytes(), Base64.NO_WRAP);
            String authStringEnc = new String(authEncBytes);
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("budgetID", String.valueOf(myID)));
            String paramString = URLEncodedUtils.format(params, "UTF-8");
            String url = (NAMESPACE + "/api/Orcamento/GetAccept?" + paramString);
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpGet Get = new HttpGet(url);

            Get.addHeader("Authorization", "Basic " + authStringEnc);
            Get.addHeader("Content-Type", "application/json; charset=utf-8");

            HttpResponse httpResponse = httpclient.execute(Get);


            // receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();

            String result = "";
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
                result = result.replace("\r\n", "");
                result = result.replace("\\", "");
                result = result.replace("rn", "");
                result = result.substring(1, result.length() - 1);

            } else
                result = null;


            return result;

        } catch (MalformedURLException ex) {
            return null;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String CancelaOrcamento(String orcamentoID) {


        byte[] authEncBytes = Base64.encode(UserPass.getBytes(), Base64.NO_WRAP);
        String authStringEnc = new String(authEncBytes);


        URL url;
        HttpURLConnection conn;

        try {
            url = new URL(NAMESPACE + "/api/Ocorrencia/PostCancel");
            String param = "occurrenceID=" + URLEncoder.encode(orcamentoID, "UTF-8") +
                    "&motive=" + URLEncoder.encode("", "UTF-8");

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();

            String response = "";
            Scanner inStream = new Scanner(conn.getInputStream());

            while (inStream.hasNextLine())
                response += (inStream.nextLine());


            response = response.replace("\r\n", "");
            response = response.replace("\\", "");
            response = response.replace("rn", "");
            response = response.substring(1, response.length() - 1);

            return response;

        } catch (MalformedURLException ex) {
            return null;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }
}

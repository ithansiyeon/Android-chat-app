package com.example.a.rrrrrsatcp_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.StringTokenizer;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;


public class MainActivity extends AppCompatActivity {

private Button startButton,rsaButton;
private EditText inputMessage,editPeerIP;
private InetAddress PeerIP2;
private InetAddress PeerIP1;
private boolean Callee = false;
private TextView receive_message;
private int SEND_PORT = 7777;//client
private int RECV_PORT = 8888;//server
private String MessageInput;
private ArrayAdapter<String> mConversationArrayAdapter;
private ListView mConversationView;
private DatagramSocket send_socket;
private DatagramSocket RTSP_send_socket;
private PrivateKey Client_RSAPrivateKey2;
static PublicKey Server_RSAPublicKey,Server_RSAPublicKey2;


private BufferedWriter RTSPBufferedWriter,RTSPBufferedWriterS,RTSPBufferedWriterdh, RTSPBufferedWriterrsa,RTSPBufferedWriterStt;
private BufferedReader RTSPBufferedReader,RTSPBufferedReaderP, RTSPBufferedReaderdh2,RTSPBufferedReaderrsa,RTSPBufferedReadertt ;
private Socket RTSPsocket,kkksocket;
private String CRLF = "\r\n";
private int RTSP_server_port = 16666;
private ServerSocket CleintSocket;
private ServerSocket serverSocket,qkedmsSocket;
private Socket RTSP_server_socket;
// private Socket kkk_socket;







TextView OwnIP;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);

OwnIP = (TextView) findViewById(R.id.OwnIP);
OwnIP.setText(getIpAddress());
editPeerIP = (EditText)findViewById(R.id.editPeerIP);
startButton = (Button) findViewById (R.id.start_button);
rsaButton = (Button) findViewById (R.id.button_rsa);
inputMessage = (EditText) findViewById (R.id.input_message);
receive_message = (TextView) findViewById (R.id.receive_message);
mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
mConversationView = (ListView) findViewById(R.id.listView1);
mConversationView.setAdapter(mConversationArrayAdapter);


startButton.setOnClickListener(startP2PSend);
rsaButton.setOnClickListener(rsaListener);



Thread startReceiveThread = new Thread(new StartReceiveThread());
startReceiveThread.start();

try {
send_socket = new DatagramSocket(SEND_PORT);
} catch (SocketException e) {
e.printStackTrace();
}
try {
RTSP_send_socket = new DatagramSocket(SEND_PORT);
}catch (SocketException e) {
Log.e("VR", "Sender SocketException");

} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}





}


//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


private final OnClickListener rsaListener = new OnClickListener() {
@Override
public void onClick(View arg0) {

//status_connect = false;
send_RTSP_rsarequest();

}
};
private final OnClickListener startP2PSend = new OnClickListener() {

@Override
public void onClick(View arg0) {
Log.d("VR", "Click OK");
startP2PSending();

}

};


public void send_RTSP_rsarequest() {

Thread start_send_RTSP_rsarequest = new Thread (new Runnable() {

@Override
public void run() {

try {


InetAddress ServerIPAddr = InetAddress.getByName(editPeerIP.getText().toString());
RTSPsocket = new Socket(ServerIPAddr, RTSP_server_port);






RTSPBufferedWriterrsa = new BufferedWriter(new OutputStreamWriter(RTSPsocket.getOutputStream()));

//Start: Create Client RSAPublic Key
KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
kpg.initialize(2048);
KeyPair kp = kpg.generateKeyPair();
KeyFactory fact = KeyFactory.getInstance("RSA");
RSAPublicKeySpec rsaPublicKeySpec = fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
BigInteger n1 = rsaPublicKeySpec.getModulus();
BigInteger e = rsaPublicKeySpec.getPublicExponent();
PublicKey Client_RSAPublicKey = generateRSAPublicKey(n1, e);
//End: Create Client RSAPublic Key


//20181start
String S_Client_RSAPublicKey = null;

S_Client_RSAPublicKey = Base64.encodeToString(Client_RSAPublicKey.getEncoded(), Base64.NO_WRAP);
Log.d("VR", "S_Client_RSAPublicKey size in string = " + S_Client_RSAPublicKey.length());
String Modified_S1 =S_Client_RSAPublicKey.replaceAll("\n", "NNNNN");
String Modified_S3 = Modified_S1.replaceAll("\r", "RRRRR");
Log.d("VR", "Modified_S after in string = " + Modified_S3);
Log.d("VR", "Modified_S3 length in string = " + Modified_S3.length());
String lineSep = System.getProperty("line.separator");
RTSPBufferedWriterrsa.write(S_Client_RSAPublicKey + CRLF);
RTSPBufferedWriterrsa.flush();


DatagramPacket RTSP_packet = new DatagramPacket(S_Client_RSAPublicKey.getBytes(), S_Client_RSAPublicKey.length(),PeerIP1,RTSP_server_port);







RSAPrivateKeySpec rsaPrivateKeySpec = fact.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);
BigInteger n2 = rsaPrivateKeySpec.getModulus();
BigInteger d = rsaPrivateKeySpec.getPrivateExponent();

PrivateKey Client_RSAPrivateKey = generateRSAPrivateKey(n2 , d);

Client_RSAPrivateKey2 = Client_RSAPrivateKey;











} catch (UnknownHostException e) {
e.printStackTrace();
} catch (IOException e) {
e.printStackTrace();
} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
} catch (InvalidKeySpecException e) {
e.printStackTrace();
}


}

});
start_send_RTSP_rsarequest.start();
}






public void startP2PSending() {

Thread startP2PSendingThread = new Thread (new Runnable() {

@Override
public void run() {

try {




MessageInput = inputMessage.getText().toString();
if(Callee == true){
PeerIP1 = PeerIP2;
}
else
{
PeerIP1 = InetAddress.getByName(editPeerIP.getText().toString());
}






final InetAddress peerIP = InetAddress.getByName(editPeerIP.getText().toString());


byte[] encryptMsg = encrypt( MessageInput.getBytes(), Server_RSAPublicKey);

String sendMsg = null;
sendMsg = Base64.encodeToString(encryptMsg, Base64.NO_WRAP);


DatagramPacket send_packet = new DatagramPacket(sendMsg.getBytes(), sendMsg.length(),PeerIP1,RECV_PORT);

send_socket.send(send_packet);
Log.d("VR", "Packet Send");

MainActivity.this.runOnUiThread(new Runnable() {
@Override
public void run() {


mConversationArrayAdapter.add("Sending from " + getIpAddress().trim() + " : " + inputMessage.getText().toString());
}
});





MainActivity.this.runOnUiThread(new Runnable() {
@Override
public void run() {

inputMessage.setText("");

}
});
//}

} catch (SocketException e) {
Log.e("VR", "Sender SocketException");

} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}


}

});
startP2PSendingThread.start();
}
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
private class StartReceiveThread extends Thread {

DatagramSocket recv_socket,RTSP_recv_socket;
byte[] receiveData =new byte[2048];
byte[] RTSP_receiveData =new byte[2048];

public void run() {

try {

recv_socket = new DatagramSocket(RECV_PORT);

RTSP_recv_socket = new DatagramSocket(RTSP_server_port);

serverSocket = new ServerSocket(RTSP_server_port);
// DatagramSocket server_socket = new DatagramSocket(RECV_PORT);
RTSP_server_socket = serverSocket.accept();

RTSPBufferedReader = new BufferedReader(new InputStreamReader(RTSP_server_socket.getInputStream()));






Log.d("VR", "Receiver Socket Created");

while (true) {

final String RTSP_receive_data = RTSPBufferedReader.readLine();

// MainActivity.this.runOnUiThread(new Runnable() {
// @Override
// public void run() {

// mConversationArrayAdapter.add("Message from " + RTSP_receive_data);
// }
// }); //서버 공개키 화면 출력 코드



byte[] Server_publicBytes = Base64.decode(RTSP_receive_data, Base64.NO_WRAP);
//new end

// PublicKey SH_key = SsH_key;

KeyFactory keyFactory = KeyFactory.getInstance("RSA");
Log.d("VR", "keyFactory OK");
X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Server_publicBytes);
Log.d("VR", "keySpec OK");

Server_RSAPublicKey = keyFactory.generatePublic(keySpec);
Log.d("VR", "Client_RSAPublicKey OK");
//End: 서버의 공개키를 받아옴 메세지 보낼때 이걸로 암호화






DatagramPacket recv_packet = new DatagramPacket(receiveData, receiveData.length);
recv_socket.receive(recv_packet);
Log.d("VR", "Packet Received");
final String receive_data = new String(recv_packet.getData(), 0 , recv_packet.getLength());




byte [] SessionBytes = Base64.decode(receive_data, Base64.NO_WRAP);

final byte[] decryptMsg = decrypt(SessionBytes, Client_RSAPrivateKey2);



final String recovered_key = new String(decryptMsg);
InetAddress sourceHost = recv_packet.getAddress() ;
PeerIP2 = sourceHost;
Callee = true;
final String sourceIP = sourceHost.getHostName();



MainActivity.this.runOnUiThread(new Runnable() {
@Override
public void run() {

mConversationArrayAdapter.add("상대방 " + recovered_key);

}
});
}

} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
} catch (InvalidKeySpecException e) {
e.printStackTrace();
}

}


}

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%




//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

private String getIpAddress() {
String ip = "";
try {
Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
while (enumNetworkInterfaces.hasMoreElements()) {
NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
while (enumInetAddress.hasMoreElements()) {
InetAddress inetAddress = enumInetAddress.nextElement();
if (inetAddress.isSiteLocalAddress()) {
ip += inetAddress.getHostAddress() + "\n";
}
}
}
} catch (SocketException e) {
e.printStackTrace();
ip += "Something Wrong! " + e.toString() + "\n";
}
return ip;
}

private static PublicKey generateRSAPublicKey(BigInteger n, BigInteger e) throws NoSuchAlgorithmException, InvalidKeySpecException {


RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(n, e);
KeyFactory fact = KeyFactory.getInstance("RSA");
PublicKey RSAPublicKey = fact.generatePublic(rsaPublicKeySpec);
// TODO Auto-generated method stub
return RSAPublicKey ;
}

private static PrivateKey generateRSAPrivateKey(BigInteger n, BigInteger d) throws NoSuchAlgorithmException, InvalidKeySpecException {

RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(n, d);
KeyFactory fact = KeyFactory.getInstance("RSA");
PrivateKey RSAPrivateKey = fact.generatePrivate(rsaPrivateKeySpec);
// TODO Auto-generated method stub
return RSAPrivateKey;
// TODO Auto-generated method stub
}


public static byte[] decrypt(byte[] input, PrivateKey key) {
byte[] decryptedKey = new byte[input.length];
try {
// get an RSA cipher object and print the provider
final Cipher cipher = Cipher.getInstance("RSA");

// decrypt the text using the private key
cipher.init(Cipher.DECRYPT_MODE, key);
decryptedKey = cipher.doFinal(input);

} catch (Exception ex) {
ex.printStackTrace();
}

return decryptedKey;
}
private static BigInteger getSharedKey(PublicKey pubKey,PrivateKey privKey)
throws NoSuchAlgorithmException, InvalidKeyException {
KeyAgreement ka = KeyAgreement.getInstance("DH");
ka.init(privKey);
ka.doPhase(pubKey, true);
byte[] b = ka.generateSecret();
BigInteger secretKey = new BigInteger(b);
return secretKey ;
}
// public static byte[] encrypt(String text, PublicKey key) {
public static byte[] encrypt(byte[] text, PublicKey key) {
byte[] cipherText = new byte[text.length];
Cipher cipher;
try {
// get an RSA cipher object and print the provider
cipher = Cipher.getInstance("RSA");
// encrypt the plain text using the public key
cipher.init(Cipher.ENCRYPT_MODE, key);
// cipherText = cipher.doFinal(text.getBytes());
cipherText = cipher.doFinal(text);
//string.getBytes(StandardCharsets.UTF_8)
} catch (Exception e) {
e.printStackTrace();
}
return cipherText;
}
public static byte[] charArray2ByteArray(char[] chars){
int length = chars.length;
byte[] result = new byte[length*2];
int i = 0;
for(int j = 0 ;j<chars.length;j++){
result[i++] = (byte)( (chars[j] & 0xFF00) >> 8 );
result[i++] = (byte)((chars[j] & 0x00FF)) ;
}
return result;
}

public static char[] byte2CharArray(byte[] data){

char[] chars = new char[data.length/2];
for(int i = 0 ;i<chars.length;i++){
chars[i] = (char)( ((data[i*2] & 0xFF) << 8 ) + (data[i*2+1] & 0xFF)) ;
}
return chars;
}
/**
* Converts a given datagram packet's contents to a String.
*/
static String stringFromPacket(DatagramPacket packet) {
return new String(packet.getData(), 0, packet.getLength());
}

/**
* Converts a given String into a datagram packet.
*/
static void stringToPacket(String s, DatagramPacket packet) {
byte[] bytes = s.getBytes();
System.arraycopy(bytes, 0, packet.getData(), 0, bytes.length);
packet.setLength(bytes.length);
}
}


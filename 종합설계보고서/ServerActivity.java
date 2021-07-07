package com.example.user.p2pchatserver;

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
import java.util.Random;
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
private PrivateKey Server_RSAPrivateKey2;
static PublicKey Client_RSAPublicKey;


private BufferedWriter RTSPBufferedWriter,RTSPBufferedWriterS,RTSPBufferedWriterdh, RTSPBufferedWriterrsa;
private BufferedReader RTSPBufferedReader,RTSPBufferedReaderP, RTSPBufferedReaderdh2,RTSPBufferedReaderrsa ;
private Socket RTSPsocket;
private String CRLF = "\r\n";
private int RTSPPORT = 16666;
private ServerSocket serverSocket;
private Socket RTSP_server_socket;
private InetAddress IPAddressClient;
private String RequestLine;
private int LEN_Key = 10;




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





//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

public void send_RTSP_rsarequest() {

Thread start_send_RTSP_rsarequest = new Thread (new Runnable() {

@Override
public void run() {

try {


InetAddress ServerIPAddr = InetAddress.getByName(editPeerIP.getText().toString());
RTSPsocket = new Socket(ServerIPAddr, RTSPPORT);






RTSPBufferedWriterrsa = new BufferedWriter(new OutputStreamWriter(RTSPsocket.getOutputStream()));




KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
kpg.initialize(2048);
KeyPair kp = kpg.generateKeyPair();
KeyFactory fact = KeyFactory.getInstance("RSA");
RSAPublicKeySpec rsaPublicKeySpec = fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
BigInteger n1 = rsaPublicKeySpec.getModulus();
BigInteger e = rsaPublicKeySpec.getPublicExponent();
PublicKey Server_RSAPublicKey = generateRSAPublicKey(n1, e);

String S_Server_RSAPublicKey = null;

S_Server_RSAPublicKey = Base64.encodeToString(Server_RSAPublicKey.getEncoded(), Base64.NO_WRAP);


String Modified_S1 =S_Server_RSAPublicKey.replaceAll("\n", "NNNNN");
String Modified_S3 = Modified_S1.replaceAll("\r", "RRRRR");

String lineSep = System.getProperty("line.separator");
RTSPBufferedWriterrsa.write(S_Server_RSAPublicKey + CRLF);
RTSPBufferedWriterrsa.flush();



RSAPrivateKeySpec rsaPrivateKeySpec = fact.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);
BigInteger n2 = rsaPrivateKeySpec.getModulus();
BigInteger d = rsaPrivateKeySpec.getPrivateExponent();

PrivateKey Server_RSAPrivateKey = generateRSAPrivateKey(n2 , d);





RTSPBufferedWriterrsa.write(S_Server_RSAPublicKey + CRLF);
RTSPBufferedWriterrsa.flush();







//Start: Create Client RSAPrivate Key


Server_RSAPrivateKey2 = Server_RSAPrivateKey;





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



byte[] encryptMsg = encrypt( MessageInput.getBytes(), Client_RSAPublicKey);

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

RTSP_recv_socket = new DatagramSocket(RTSPPORT);


serverSocket = new ServerSocket(RTSPPORT);
// DatagramSocket server_socket = new DatagramSocket(RECV_PORT);
RTSP_server_socket = serverSocket.accept();
// IPAddressClient = RTSP_server_socket.getInetAddress();
RTSPBufferedReader = new BufferedReader(new InputStreamReader(RTSP_server_socket.getInputStream()));
RTSPBufferedWriterS = new BufferedWriter(new OutputStreamWriter(RTSP_server_socket.getOutputStream()));





while (true) {


final String RTSP_receive_data = RTSPBufferedReader.readLine();



// MainActivity.this.runOnUiThread(new Runnable() {
// @Override
// public void run() {

// mConversationArrayAdapter.add("상대방 " +RTSP_receive_data);

// }
// }); 공개키 화면 출력 코드 



byte[] Client_publicBytes = Base64.decode(RTSP_receive_data, Base64.NO_WRAP);
//new end

// PublicKey SH_key = SsH_key;

KeyFactory keyFactory = KeyFactory.getInstance("RSA");
Log.d("VR", "keyFactory OK");
X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Client_publicBytes);
Log.d("VR", "keySpec OK");

// PublicKey Client_RSAPublicKey = keyFactory.generatePublic(keySpec);
Client_RSAPublicKey = keyFactory.generatePublic(keySpec);
Log.d("VR", "Client_RSAPublicKey OK");



InetAddress sourceHost2 = RTSP_server_socket.getInetAddress() ;
final String sourceIP2 = sourceHost2.getHostName();




MainActivity.this.runOnUiThread(new Runnable() {
@Override
public void run() {

editPeerIP.setText(sourceIP2);

}
});








DatagramPacket recv_packet = new DatagramPacket(receiveData, receiveData.length);
recv_socket.receive(recv_packet);
Log.d("VR", "Packet Received");
final String receive_data = new String(recv_packet.getData(), 0 , recv_packet.getLength());





byte [] SessionBytes = Base64.decode(receive_data, Base64.NO_WRAP);

final byte[] decryptMsg = decrypt(SessionBytes, Server_RSAPrivateKey2);


final String recovered_key = new String(decryptMsg);


InetAddress sourceHost = recv_packet.getAddress() ;
PeerIP2 = sourceHost;
Callee = true;
final String sourceIP = sourceHost.getHostName();

MainActivity.this.runOnUiThread(new Runnable() {
@Override
public void run() {

mConversationArrayAdapter.add("상대방 " +recovered_key);

}
});








}

} catch (SocketException e) {
e.printStackTrace();
} catch (IOException e) {
e.printStackTrace();
} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
} catch (InvalidKeySpecException e) {
e.printStackTrace();
}


}

}

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




import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GetPublicIP {
     

     /**/
     /**
      * ��ȡ�����ص��ļ�����ַ���(IP��ַ)
      * 
      * @return ��ȡ������IP��ַ
      * @throws Exception
      */
     public static String GetPublicIP(String urlStr, String tempSaveStr) {

         // ���ز��� - ��ʼ �����������ļ���ȡ���IP��ַ������Ϊ��ʱ�ļ�IP.shtml
         int chByte = 0; // ���������������ݳ���
         URL url = null; // �����url��ַ
         HttpURLConnection httpConn = null; // http����
         InputStream in = null; // ������
         FileOutputStream out = null; // �ļ������
         try {
             url = new URL(urlStr);
             httpConn = (HttpURLConnection) url.openConnection();
             HttpURLConnection.setFollowRedirects(true);
             httpConn.setRequestMethod("GET");
             //ģ�� IE ����
             httpConn.setRequestProperty("User-Agent",
                     "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");

             in = httpConn.getInputStream();
             out = new FileOutputStream(new File("tempSaveStr"));

             chByte = in.read();
             while (chByte != -1) {
                 out.write(chByte);
                 chByte = in.read();
             }
         } catch (MalformedURLException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             try {
                 out.close();
                 in.close();
                 httpConn.disconnect();
             } catch (Exception ex) {
                 ex.printStackTrace();
             }
         }
         // ���ز��� - ����

         // ��ȡIP���� - ��ʼ : ����ʱ�ļ�IP.shtml�ж�ȡIP��ַ
         String IP = null;
         try {
             BufferedReader br = new BufferedReader(
                     new FileReader("tempSaveStr"));
             IP = br.readLine();
             br.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
         // ��ȡIP���� - ����

         // ɾ������ - ��ʼ ��ɾ����ʱ�ļ�IP.shtml
         try {
             java.io.File myDelFile = new java.io.File("tempSaveStr");
             myDelFile.delete();
         } catch (Exception e) {
             e.printStackTrace();
        }
         // ɾ������ - ����

         return IP;
     }
}// 
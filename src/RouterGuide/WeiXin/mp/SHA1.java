/**
 * �Թ���ƽ̨���͸������˺ŵ���Ϣ�ӽ���ʾ������.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package RouterGuide.WeiXin.mp;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * SHA1 class
 *
 * ���㹫��ƽ̨����Ϣǩ���ӿ�.
 */
public class SHA1 {

	/**
	 * ��SHA1�㷨���ɰ�ȫǩ��
	 * @param token Ʊ��
	 * @param timestamp ʱ���
	 * @param nonce ����ַ���
	 * @param encrypt ����
	 * @return ��ȫǩ��
	 * @throws AesException 
	 */
	public static String getSHA1(String token, String timestamp, String nonce, String encrypt) throws AesException{
		try {
			String[] array = new String[] { token, timestamp, nonce, encrypt };
			StringBuilder sb = new StringBuilder();
			// �ַ�������
			Arrays.sort(array);
			for (int i = 0; i < 4; i++) {
				sb.append(array[i]);
			}
			// SHA1ǩ������
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(sb.toString().getBytes());
			byte[] digest = md.digest();

			StringBuilder hexstr = new StringBuilder(digest.length*2);
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}
	/**
	 * ��SHA1�㷨���ɰ�ȫǩ��
	 * @param token Ʊ��
	 * @param timestamp ʱ���
	 * @param nonce ����ַ���
	 * @param encrypt ����
	 * @return ��ȫǩ��
	 * @throws AesException 
	 */
	public static String getSHA1(String token, String timestamp, String nonce) throws AesException{
		try {
			String[] array = new String[]{ token,timestamp,nonce};
			StringBuilder sb = new StringBuilder(token.length()+timestamp.length()+nonce.length());
			// �ַ�������
			Arrays.sort(array);
			for (int i = 0; i < 3; i++) {
				sb.append(array[i]);
			}
			// SHA1ǩ������
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(sb.toString().getBytes());
			byte[] digest = md.digest();

			StringBuilder hexstr = new StringBuilder(digest.length*2);
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}
}
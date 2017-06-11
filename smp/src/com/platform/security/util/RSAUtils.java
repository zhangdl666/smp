package com.platform.security.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAUtils {
	// KeyPair is a simple holder for a key pair.
	private static final KeyPair keyPair = initKey();

	/**
	 * ��ʼ������������key pair���ṩprovider��random
	 * 
	 * @return KeyPair instance
	 */
	private static KeyPair initKey() {

		try {
			// ���provider
			Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
			Security.addProvider(provider);
			// �������ڰ�ȫ���ܵ������
			SecureRandom random = new SecureRandom();

			// KeyPairGenerator is used to generate pairs of public and private
			// keys,
			// which is constructed using the getInstance() factory methods.
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA",
					provider);
			generator.initialize(1024, random);
			return generator.generateKeyPair();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ����public key
	 * 
	 * @return public key�ַ���
	 */
	public static String generateBase64PublicKey() {
		PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// encodeBase64(): Encodes binary data using the base64
		// algorithm but does not chunk the output.
		// getEncoded():����key��ԭʼ������ʽ
		return new String(Base64.encodeBase64(publicKey.getEncoded()));
	}

	/**
	 * ��������
	 * 
	 * @param string
	 *            ��Ҫ���ܵ��ַ���
	 * @return �ƽ�֮����ַ���
	 */
	public static String decryptBase64(String string) {
		// decodeBase64():��Base64���ݽ���Ϊ"��λ�ֽڡ�����
		return new String(decrypt(Base64.decodeBase64(string.getBytes())));
	}

	private static byte[] decrypt(byte[] byteArray) {
		try {
			Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
			Security.addProvider(provider);
			// Cipher: �ṩ���ܺͽ��ܹ��ܵ�ʵ��
			// transformation: "algorithm/mode/padding"
			Cipher cipher = Cipher
					.getInstance("RSA/ECB/PKCS1Padding", provider);
			PrivateKey privateKey = keyPair.getPrivate();
			// ��ʼ��
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			// doFinal(): ���ܻ��߽�������
			byte[] plainText = cipher.doFinal(byteArray);
			return plainText;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(RSAUtils.generateBase64PublicKey());
	}
}

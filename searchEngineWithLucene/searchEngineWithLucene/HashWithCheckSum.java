//Napoleon Oikonomou

package searchEngineWithLucene;

import java.io.FileInputStream;
import java.security.MessageDigest;

class HashWithCheckSum {
	static String getCheckSum(String datafile) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		FileInputStream fis = new FileInputStream(datafile);
		byte[] dataBytes = new byte[1024];
		int nread;
		while ((nread = fis.read(dataBytes)) != -1) {
			md.update(dataBytes, 0, nread);
		}
		byte[] mdbytes = md.digest();
		StringBuilder sb = new StringBuilder("");
		for (byte mdbyte : mdbytes) {
			sb.append(Integer.toString((mdbyte & 0xff) + 0x100, 16).substring(1));
		}
		fis.close();
		return (sb.toString());
	}
}

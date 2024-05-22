import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class CriptoAES {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Você deseja criptografar ou descriptografar?");
        String escolha = scanner.nextLine().trim().toLowerCase();

        try {
            if ("criptografar".equals(escolha)) {
                System.out.println("Digite a mensagem a ser criptografada:");
                String mensagem = scanner.nextLine();
                SecretKey chave = gerarChaveAES();
                String chaveBase64 = Base64.getEncoder().encodeToString(chave.getEncoded());
                String mensagemCifrada = cifrarAES(mensagem, chave);
                System.out.println("Mensagem Cifrada: " + mensagemCifrada);
                System.out.println("Chave (Base64): " + chaveBase64);
            } else if ("descriptografar".equals(escolha)) {
                System.out.println("Digite a mensagem a ser descriptografada:");
                String mensagemCifrada = scanner.nextLine();
                System.out.println("Digite a chave (Base64):");
                String chaveBase64 = scanner.nextLine();
                SecretKey chave = new SecretKeySpec(Base64.getDecoder().decode(chaveBase64), "AES");
                String mensagemDecifrada = decifrarAES(mensagemCifrada, chave);
                System.out.println("Mensagem Decifrada: " + mensagemDecifrada);
            } else {
                System.out.println("Escolha inválida.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    public static SecretKey gerarChaveAES() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // chave de 256 bits
        return keyGen.generateKey();
    }

    public static String cifrarAES(String mensagem, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        byte[] bytesCifrados = cipher.doFinal(mensagem.getBytes());
        return Base64.getEncoder().encodeToString(bytesCifrados);
    }

    public static String decifrarAES(String mensagemCifrada, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, chave);
        byte[] bytesDecifrados = cipher.doFinal(Base64.getDecoder().decode(mensagemCifrada));
        return new String(bytesDecifrados);
    }
}

package test;
import java.util.Scanner;

public class DenemMain {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
		Scanner input=new Scanner(System.in);
        MyDaireAlanHesap daire=new MyDaireAlanHesap();
        System.out.print("Daire Sayisini giriniz :");
        daire.dairesay = input.nextInt();
        System.out.print("Yarı Çap gir :");
        daire.yariCap = input.nextInt();
        System.out.print("Dairelerin alanı :" + daire.alan());
//        System.out.println(daire.alan());
    }
}
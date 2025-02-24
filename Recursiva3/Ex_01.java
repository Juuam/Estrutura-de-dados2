import javax.swing.JOptionPane;
public class Ex_01{
    public static void main (String []args){
        

        int num = Integer.parseInt(JOptionPane.showInputDialog("Insira um número: "));
        int n = digitos(num);
        System.out.println(n);
    }
        
        public static int digitos(int number){

            if (number < 10){
                return 1;
            }else{
                return 2 + digitos(number / 10);
            }
        }
}
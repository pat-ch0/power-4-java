import java.io.Console;
public class Joueur {
    private String name;
    private Couleur color;

    public Joueur(String n){
        this.name = n;
    }

    public String name(){
        return this.name;
    }

    public void setName(String n){
        this.name = n;
    }

    public Couleur hisColor(){
        return this.color;
    }

    public void setColor(Couleur coul){
        this.color = coul;
    }

    public int makeAMove(){
        Console console = System.console();
        boolean verif = false;
        String str = "";
        while(!verif){
            verif = true;
            str = console.readLine(this + "\033[1;34m , choisissez une valeur entre 1 et 7 : \033[0m");
            for(int i = 0; i < str.length(); i++){
                if(!(Character.isDigit(str.charAt(i)))){
                    verif = false;
                }
            }
            if(!verif){
                System.out.println("\033[1;34m/!\\ Valeur invalide !\033[0m");
            }
        }
        return (Integer.parseInt(str) - 1);
    }

    @Override
    public String toString(){
        if(this.color == Couleur.R){
            return "\033[1;31m"+this.name+"\033[0m";
        }
        else if (this.color == Couleur.J){
            return "\033[1;33m"+this.name+"\033[0m";
        }
        else{
            return this.name;
        }
    }
}

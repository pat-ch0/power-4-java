import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class Jeu {
    private Joueur j1;
    private Joueur j2;
    private Grille gate;
    private int turn;

    public Jeu(Joueur a, Joueur b, Grille g){
        this.j1 = a;
        this.j2 = b;
        this.gate = g;
    }

    public void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void Load(Path file){
        if(Files.exists(file)){
            try{
                String w = new String(Files.readAllBytes(file));
                char c;
                int index = 0;
                this.gate.initGrille();
                for (int i = 0; i < 6; i++){
                    for(int j = 0; j < 7; j++){
                        index = j + (i * 7);
                        c = w.charAt(index);
                        if(c == 'O'){
                            this.gate.getGame()[i][j].setColor(Couleur.O);
                        }
                        else if(c == 'R'){
                            this.gate.getGame()[i][j].setColor(Couleur.R);
                        }
                        else if(c == 'J'){
                            this.gate.getGame()[i][j].setColor(Couleur.J);
                        }

                    }
                }
                index = 43;
                for(int i = 0; i < 7; i++){
                    this.gate.setFree(i, Character.getNumericValue(w.charAt(index)));
                    index += 1;
                }
                String name1 = "";
                String name2 = "";
                // pour name1
                index += 1; 
                c = w.charAt(index);
                while(c != ' '){
                    name1 += c;
                    index += 1;
                    c = w.charAt(index);
                }
                this.j1.setName(name1);
                index += 1;
                c = w.charAt(index);
                if(c == 'R'){
                    this.j1.setColor(Couleur.R);
                    this.j2.setColor(Couleur.J);
                }
                else{
                    this.j1.setColor(Couleur.J);
                    this.j2.setColor(Couleur.R);
                }
                //pour name2
                index += 2;
                c = w.charAt(index);
                while(c != ' '){
                    name2 += c;
                    index += 1;
                    c = w.charAt(index);
                }
                this.j2.setName(name2);
                //turn
                index += 3;
                this.turn = Character.getNumericValue(w.charAt(index));
            } catch (IOException e) {
                System.out.println("/!\\ Erreur lors de la lecture de la sauvegarde !");
                e.printStackTrace();
            }
            
        }
        else{
            System.out.println("Oops, il y a un soucis sur l'existance d'une sauvegarde");
        }
    }




    public void Save(Path file){
        if(Files.exists(file)){
            try{
                String w = "";
                for(int i = 0; i < 6; i++){
                    for(int j = 0; j < 7; j++){
                        w += this.gate.getGame()[i][j].getColor();
                    }
                }
                w += '\n';
                for(int i = 0; i < 7; i++){
                    w += String.valueOf(this.gate.getFree()[i]);
                }
                w += '\n';
                w += this.j1.name() +" "+this.j1.hisColor() + '\n';
                w += this.j2.name() +" "+this.j2.hisColor() + '\n';
                w += Integer.toString(this.turn);
                Files.write(file, w.getBytes());
            } catch (IOException e) {
                System.out.println("/!\\ Erreur lors de l'écriture de la sauvegarde !");
                e.printStackTrace();
            }
            
        }
        else{
            System.out.println("Oops, il y a un soucis sur l'existance d'une sauvegarde");
        }
    }

    public int doesExist(Path file){ //vérifie si le contenu de save.txt est vide ou non
        if(Files.exists(file)){
            try{
                String w = new String(Files.readAllBytes(file));
                if(w.equals("")){
                    return 0;
                }
                else{
                    return 1;
                }
            } catch (IOException e) {
                System.out.println("/!\\ Erreur lors de la detection de la sauvegarde !");
                e.printStackTrace();
                return -1;
            }
            
        }
        else{
            System.out.println("Oops, il y a un soucis sur l'existance d'une sauvegarde");
            return -1;
        }
    }

    public void Play(){
        Path p = Paths.get("save.txt");
        Console console = System.console();
        int load = this.doesExist(p);
        String L = "non";
        boolean end = false;
        int move = -1; //choix d'un joueur
        if(load == 1){
            L = console.readLine("Une sauvegarde a ete detectee, souhaitez-vous la charger (oui/non) ?\n");
            if(L.equals("oui")){
                this.Load(p);
            }
            else if ( (!L.equals("non")) && (!L.equals("oui")) ){
                throw new IllegalArgumentException("Invalid choice between 'oui' and 'non' ");
            }
        }
        else if (load == -1){
            throw new IllegalAccessError();
        }
        if(L.equals("non")){
            this.turn = 1; //Tour du joueur 1 ou du joueur 2
            String name1 = console.readLine("Joueur 1, veuillez saisir votre nom : ");
            this.j1.setName(name1);
            String name2 = console.readLine("Joueur 2, veuillez saisir votre nom : ");
            this.j2.setName(name2);
            String choice = console.readLine(this.j1 + ", veuillez choisir une couleur : Rouge ou Jaune : ");
            if(choice.equals("Rouge")){
                this.j1.setColor(Couleur.R);
                this.j2.setColor(Couleur.J);
            }
            else if(choice.equals("Jaune")){
                this.j1.setColor(Couleur.J);
                this.j2.setColor(Couleur.R);
            }
            else{
                System.out.println(choice);
                System.out.println(choice.length());
                throw new IllegalArgumentException("Couleur non valide");
            }
            this.gate.initGrille();
        }
        this.gate.initCases();
        this.clearScreen();
        System.out.println(this.gate);
        outer: //Cela va nous permettre de quitter la boucle while étant à la base infinie lorsque la partie est finie
        while(true){
            if(this.gate.isFull()){
                this.turn = 0;
                break outer;
            }
            if(this.turn == 1){
                boolean b = true;
                while(b){
                    move = this.j1.makeAMove();
                    if ((0 <= move) && (move <= 6)){
                        if(this.gate.getFree()[move] != 9){
                            b = false;
                        }
                        else{
                            this.clearScreen();
                            System.out.println("\033[1;34mAttention, veuillez saisir une colonne non-pleine !\n\033[0m");
                            System.out.println(this.gate);
                        }
                    }
                    else if (move == 7) {
                        this.Save(p);
                        this.turn = -1;
                        break outer;
                    }
                    else {
                        this.clearScreen();
                        System.out.println("\033[1;34mAttention, veuillez saisir une colonne valide !\n\033[0m");
                        System.out.println(this.gate);
                    }
                }
                end = this.gate.put(move, this.j1.hisColor()); //Le joueur joue un coup et la méthode put retourne un boolean disant si ce coup marque la victoire ou non
                this.turn = 2;
            }
            else if (this.turn == 2){
                boolean b = true;
                while(b){
                    move = this.j2.makeAMove();
                    if ((0 <= move) && (move <= 6)){
                        if(this.gate.getFree()[move] != 9){
                            b = false;
                        }
                        else{
                            this.clearScreen();
                            System.out.println("\033[1;34mAttention, veuillez saisir une colonne non-pleine !\n\033[0m");
                            System.out.println(this.gate);
                        }
                    }
                    else if (move == 7) {
                        this.Save(p);
                        this.turn = -1;
                        break outer;
                    }
                    else {
                        this.clearScreen();
                        System.out.println("\033[1;34mAttention, veuillez saisir une colonne valide !\n\033[0m");
                        System.out.println(this.gate);
                    }
                }
                end = this.gate.put(move, this.j2.hisColor()); //Le joueur joue un coup et la méthode put retourne un boolean disant si ce coup marque la victoire ou non
                this.turn = 1;
            }
            this.clearScreen();
            System.out.println(this.gate);
            if(end){
                break outer;
            }
            
        }
        if(this.turn == 2){ //Si la partie se termine sur un tour donné (1 ou 2), c'est que le joueur précédent a gagné la partie
            System.out.println("\033[1;32mVictoire de \033[0m" + this.j1);
        }
        else if(this.turn == 1){
            System.out.println("\033[1;32mVictoire de \033[0m" + this.j2);
        }
        else if (this.turn == 0) {
            System.out.println("\033[1;34mEgalite \033[0m");
        }
        else {
            System.out.println("\033[1;34mLa partie a bien ete sauvegarde.\033[0m");
        }
    }

    public static void main(String[] args){
        Jeu jouer = new Jeu(new Joueur(""), new Joueur(""), new Grille());
        jouer.Play();
    }
}

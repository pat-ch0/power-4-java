public class Grille {
    private Cellule[][] game;
    private int[] free;
    public Grille(){
        this.game = new Cellule[6][7];
        this.free = new int[7];
    }

    public Cellule[][] getGame() {
        return this.game;
    }

    public int[] getFree(){
        return this.free;
    }

    public void setFree(int ind, int v){
        this.free[ind] = v;
    }

    public void initGrille(){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                this.game[i][j] = new Cellule(Couleur.O);
            }
        }
        for(int i = 0; i < 7; i++){
            this.free[i] = 5;
        }
    }

    public void initCases(){
        for(int i = 0; i < 6; i++){
            for(int index = 0; index < 7; index++){
                if(i == 0 && index == 0){
                    this.game[i][index].addNeig(this.game[i+1][index], Direction.BAS);
                    this.game[i][index].addNeig(this.game[i][index+1], Direction.DROITE);
                }
                else if(i == 5 && index == 0){
                    this.game[i][index].addNeig(this.game[i-1][index], Direction.HAUT);
                    this.game[i][index].addNeig(this.game[i][index+1], Direction.DROITE);
                }
                else if(i == 0 && index == 6){
                    this.game[i][index].addNeig(this.game[i+1][index], Direction.BAS);
                    this.game[i][index].addNeig(this.game[i][index-1], Direction.GAUCHE);
                }
                else if(i == 5 && index == 6){
                    this.game[i][index].addNeig(this.game[i-1][index], Direction.HAUT);
                    this.game[i][index].addNeig(this.game[i][index-1], Direction.GAUCHE);
                }
                //Le reste des bords
                else if(i == 0 && index > 0 && index < 6){ //ligne du haut
                    this.game[i][index].addNeig(this.game[i+1][index], Direction.BAS);
                    this.game[i][index].addNeig(this.game[i][index-1], Direction.GAUCHE);
                    this.game[i][index].addNeig(this.game[i][index+1], Direction.DROITE);
                }
                else if(i == 5 && index > 0 && index < 6){ //ligne du bas
                    this.game[i][index].addNeig(this.game[i-1][index], Direction.HAUT);
                    this.game[i][index].addNeig(this.game[i][index-1], Direction.GAUCHE);
                    this.game[i][index].addNeig(this.game[i][index+1], Direction.DROITE);
                }
                else if(index == 0 && i > 0 && i < 5){ //colonne de gauche
                    this.game[i][index].addNeig(this.game[i-1][index], Direction.HAUT);
                    this.game[i][index].addNeig(this.game[i+1][index], Direction.BAS);
                    this.game[i][index].addNeig(this.game[i][index+1], Direction.DROITE);
                }
                else if(index == 6 && i > 0 && i < 5){ //colonne de droite
                    this.game[i][index].addNeig(this.game[i-1][index], Direction.HAUT);
                    this.game[i][index].addNeig(this.game[i+1][index], Direction.BAS);
                    this.game[i][index].addNeig(this.game[i][index-1], Direction.GAUCHE);
                }
                else { //non dans un bord
                    this.game[i][index].addNeig(this.game[i-1][index], Direction.HAUT);
                    this.game[i][index].addNeig(this.game[i+1][index], Direction.BAS);
                    this.game[i][index].addNeig(this.game[i][index-1], Direction.GAUCHE);
                    this.game[i][index].addNeig(this.game[i][index+1], Direction.DROITE);
                }
            }
        }
    }

    public Boolean put(int index, Couleur j){ // index correspond à la colonne choisie par le joueur
        if(index < 0 || index > 6) {
            throw new IllegalArgumentException("Choix de colonne non valide !");
        }
        int i = this.free[index];
        this.game[i][index].setColor(j);
        //Les 4 coins
        if(i == 0 && index == 0){
            this.game[i][index].addNeig(this.game[i+1][index], Direction.BAS);
            this.game[i][index].addNeig(this.game[i][index+1], Direction.DROITE);
        }
        else if(i == 5 && index == 0){
            this.game[i][index].addNeig(this.game[i-1][index], Direction.HAUT);
            this.game[i][index].addNeig(this.game[i][index+1], Direction.DROITE);
        }
        else if(i == 0 && index == 6){
            this.game[i][index].addNeig(this.game[i+1][index], Direction.BAS);
            this.game[i][index].addNeig(this.game[i][index-1], Direction.GAUCHE);
        }
        else if(i == 5 && index == 6){
            this.game[i][index].addNeig(this.game[i-1][index], Direction.HAUT);
            this.game[i][index].addNeig(this.game[i][index-1], Direction.GAUCHE);
        }
        //Le reste des bords
        else if(i == 0 && index > 0 && index < 6){ //ligne du haut
            this.game[i][index].addNeig(this.game[i+1][index], Direction.BAS);
            this.game[i][index].addNeig(this.game[i][index-1], Direction.GAUCHE);
            this.game[i][index].addNeig(this.game[i][index+1], Direction.DROITE);
        }
        else if(i == 5 && index > 0 && index < 6){ //ligne du bas
            this.game[i][index].addNeig(this.game[i-1][index], Direction.HAUT);
            this.game[i][index].addNeig(this.game[i][index-1], Direction.GAUCHE);
            this.game[i][index].addNeig(this.game[i][index+1], Direction.DROITE);
        }
        else if(index == 0 && i > 0 && i < 5){ //colonne de gauche
            this.game[i][index].addNeig(this.game[i-1][index], Direction.HAUT);
            this.game[i][index].addNeig(this.game[i+1][index], Direction.BAS);
            this.game[i][index].addNeig(this.game[i][index+1], Direction.DROITE);
        }
        else if(index == 6 && i > 0 && i < 5){ //colonne de droite
            this.game[i][index].addNeig(this.game[i-1][index], Direction.HAUT);
            this.game[i][index].addNeig(this.game[i+1][index], Direction.BAS);
            this.game[i][index].addNeig(this.game[i][index-1], Direction.GAUCHE);
        }
        else { //pas dans un bord
            this.game[i][index].addNeig(this.game[i-1][index], Direction.HAUT);
            this.game[i][index].addNeig(this.game[i+1][index], Direction.BAS);
            this.game[i][index].addNeig(this.game[i][index-1], Direction.GAUCHE);
            this.game[i][index].addNeig(this.game[i][index+1], Direction.DROITE);
        }
        if(this.free[index] == 0){
            this.free[index] = 9; //choix de valeur signifiant colonne pleine. Si la valeur est négative, elle risque de nuire au fonctionnement du chargement d'une sauvegarde du à des caractères en trop ('-') .
        }
        else {
            this.free[index] -= 1; 
        }
        return this.game[i][index].check();
    }


    public Boolean isFull(){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                if (this.game[i][j].getColor() == Couleur.O){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString(){
        String str = "\033[0;34m";
        for(int i = 0; i < 6; i++){
            str += "| ";
            for(int j = 0; j < 7; j++){
                str += this.game[i][j] + " | ";  
            }
            str += "\n-----------------------------\n";
        
        }
        return str+"  1   2   3   4   5   6   7\nSaisissez 8 pour sauvegarder et quitter.\033[0m";
    }
}
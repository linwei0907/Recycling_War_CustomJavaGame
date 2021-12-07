public class Cans {
    public Cans(Scene scene){
        Can can[] = new Can[5];

         for(int i = 0; i < 5; i++){
             can[i] = new Can(scene, "ch.png", 50, 50);
             can[i].setPosition(50, 50 + (50*i));
             can[i].setFocusable(true);
         }


    }
}

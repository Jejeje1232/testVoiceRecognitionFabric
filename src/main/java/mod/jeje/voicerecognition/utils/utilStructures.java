package mod.jeje.voicerecognition.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class utilStructures {
    public static class ARGB {
        public int Alpha;
        public int Red;
        public int Green;
        public int Blue;
        public ARGB(int Alpha, int Red, int Green, int Blue){
            this.Alpha = Alpha;
            this.Red = Red;
            this.Green = Green;
            this.Blue = Blue;
        }
    }

    public static class TextPool {
        /*
        This class initiates a "text function pool", a pool that contains multiple Strings and ONE function that requires a String to execute on the call of a method (next()).
         */
        private List<String> textList;
        private Consumer<String> theFunction;
        public TextPool(Consumer<String> function){
            this.textList = new ArrayList<String>();
            this.theFunction = function;
        }

        public void test(){System.out.println(this.textList);}

        public boolean isEmpty(){return this.textList.isEmpty();}

        public void add(String string){
            this.textList.add(string);
        }

        public void next(){
            if (this.textList.isEmpty()){return;}
            this.theFunction.accept(this.textList.get(0));
            this.textList.remove(0);
        }
    }
}

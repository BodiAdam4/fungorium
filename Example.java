public class Example {
    //Ez itt egy példa a Logger osztály használatához

    public Example(String objectName) {
        Logger.Constructor(this, objectName);
        Logger.Log("Szia ez itt a konstruktor törzse");

        Hello();
        Foo(12,24.2);

        Logger.FunctionEnd();
    }

    
    public Example(String objectName, int num) {
        Logger.Constructor(this, objectName, new Object[]{num});
        Logger.Log("Szia ez itt a paraméteres konstruktor törzse");

        Hello();
        Foo(12,24.2);

        Logger.FunctionEnd();
    }

    public void Hello() {
        Logger.FunctionStart(this, "Hello");

        Logger.Log("Ez itt egy paraméter nélküli függvény");
        Logger.Log("Ez itt egy random kiírás");

        Logger.FunctionEnd();
    }

    public void Foo(int szam1, double szam2) {
        Logger.FunctionStart(this, "Foo", new Object[]{szam1, szam2});

        Logger.Log("Ez itt egy paraméteres függvény");
        Logger.Log("Ez itt a második random kiírás a Foo függvényből");

        Hello();

        Logger.FunctionEnd();
    }

    public void Outside() {
        Logger.FunctionStart(this, "Outside");

        Logger.Log("Ez a függvény kintről lett meghívva");

        Foo(2,3);

        Logger.FunctionEnd();
    }
}

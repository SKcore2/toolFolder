package application;

public enum Command {
    Status(1),
    Add(2),
    Commit(3),
    Push(4),
    Reset(5),
    Pull(6),
    Branch(7);




    // フィールドの定義
    private int id;

    // コンストラクタの定義
    private Command(int id) {
        this.id = id;
    }
}

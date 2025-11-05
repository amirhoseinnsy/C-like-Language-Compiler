void foo() {
    printf("heb");
}
void foo(int x) {
    printf("heb");
}
int foo(int x, char y) {
    printf("heb");
}
void moo(char x, char y) {
    printf("%s%s", x, y);
    moo(20);
}
void shoo() {
    int x;
    foo(foo(x), x);
    moo(10);
}
int main(){
    foo(3);
    foo(3, 5); // amdan ro in error nazashtam ke dar nazar begirid ba esm va tedad argoman bayad tashkhis bedid
    moo("Hello", "World");
}
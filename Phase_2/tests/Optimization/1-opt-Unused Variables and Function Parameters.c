void process(int x) {



    printf("%d\n", x);
}
void f2() {
    printf();
}
void f1() {
    f2();
}
int main(){
    process(10);
    f1();
}
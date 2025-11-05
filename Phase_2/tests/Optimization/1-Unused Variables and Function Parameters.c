void process(int x, int unused_param) {
    int a = 5;
    int b = 10;
    int c = 15;
    printf("%d\n", x);
}
void f2(int x) {
    printf();
}
void f1(int x) {
    f2(x);
}
int main(){
    process(10, 20);
    f1(100);
}
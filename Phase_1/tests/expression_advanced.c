int afunc(int b, int c) {
    return b + c;
}

int** afunc2(int b, int c) {
    int** arr = (int**)malloc(sizeof(int*) * 2);
    arr[0] = (int*)malloc(sizeof(int) * 2);
    arr[1] = (int*)malloc(sizeof(int) * 2);
    return arr;
}

void main()
{
    int a,b,c;
    int arr[5];
    int* arr2[5];

    a;
    1;
    "hi";
    (a);
    arr[b];
    afunc(b,c);
    ~a++;
    !a--;
    !afunc(b,c);
    &arr[b];
    !arr[b];
    !(a*a);
    ~arr[b];
    -arr[b];
    -a++;
    *arr2[b];
    ++arr[0];
    ++afunc2(b,c)[1][arr[1]+-c++];
    (int)++a;
    (int)!a;
    (int)~a;
    (int)a * (int)b;
    (int)a / (int)b;
    (int)a % (int)b;
    a*b + a/b - b%a;
    a+b << a;
    a-b >> a;
    a<<b > b>>a <= a>>b >= b<<a;
    a>b != b<a;
    a>b == b<a != a>b;
    a==b & b!=a & a==a;
    a&b ^ a&a ^ b&c;
    a^b | b^b | a^a;
    a|b && b|c && c|a;
    a&&b || b&&c || c&&b;
    a||b ? a*=b : b||c ? a||c : c||c;
    a = a ? a : b;

    int arr3[] = {a=b, c=a, b=c};
}
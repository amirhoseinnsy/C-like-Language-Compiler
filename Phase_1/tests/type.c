void main()
{
    char v1;
    double v2;
    float v3;
    int v4;
    long v5;
    short v6;

    const double vv1;
    long long vv2;
    long long int vv3;
    long int vv4;

    int *t1;
    char *const t2;
    void *t3;
    int *const *t4;
    int *const *const t5;
    int ** t6;
    int (*t7);


    int a1[10];
    char a2[10][20];

    int *pa1[10];
    int (*pa1)[10];
    char *pa3[10][20];

    int (*fptr)(int, int);
    int (*fptr2)(int, double (*fptr3)(char, int));
}
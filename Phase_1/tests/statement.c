int main()
{
    int a;
    a = 1;

    switch (a)
    {
    case 1:
        1;
        break;
    case 2:
        2;
    case 3:
    default:
        3;
        break;
    }

    if (1)
        1;
    else {
        2;
    }

    if (1)
        1;
    else if (0) {
        2;
    } else {
        3;
    }

    while(a)
        1;

    while(1) {1;}
    while(1) {continue;}
    while(1) {break;}

    do
        1;
    while(1);

    do {1;2;}
    while(1);

    for (;;)
        1;

    for (;;) {1;2;}

    for (a=1;;) 1;
    for (a+3;;) 1;
    for (int i;;) 1;
    for (int i=0; i < 10;) 1;
    for (int i=0; i < 10;i++) 1;

    return;

    return a;
}
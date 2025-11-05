void main()
{
    int a = 5;
    int b = 7;

    int tmp = a;
    tmp = a++;
    tmp = a--;
    tmp = ++a;
    tmp = --a;
    tmp += b;
    tmp -= a;
    tmp *= 2.7;
    tmp /= a;
    tmp %= b;
    tmp &= 8;
    tmp |= b;
    tmp ^= b;
    tmp <<= b;
    tmp >>= 56;
    tmp = a + b;
    tmp = a - 7;
    tmp = a * b;
    tmp = a / 2.34;
    tmp = a % b;
    tmp = a & 12;
    tmp = a | b;
    tmp = a ^ b;
    tmp = a << b;
    tmp = 7 >> b;
    tmp = a == b;
    tmp = a != b;
    tmp = a > b;
    tmp = 2 < b;
    tmp = a >= b;
    tmp = a <= b;
    tmp = a && b;
    tmp = 0 || b;
    tmp = a ? 1 : b;
    tmp = !a;
    tmp = ~a;
    tmp = -a;
    tmp = a = b;
}
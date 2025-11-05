double returnDoubleAsInt() {
    return 42;
}

int returnIntAsDouble() {
    return 3.14;
}

int main() {
    double d = returnDoubleAsInt();
    int i = returnIntAsDouble();
    return 0;
}
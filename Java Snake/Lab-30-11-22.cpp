// Online C++ compiler to run C++ program online
#include <iostream>

bool Foo(std::string& str){
    str+="Foo";
    return true;
}
bool Bar(std::string& str){
    str+="Bar";
    return false;
}

int main() {
    std::string str;
    
    bool a = true, b = false;
    
    std::cout << Foo(str)||Bar(str);
    std::cout<<str;
    // Write C++ code here


    return 0;
}
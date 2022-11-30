#include <iostream>
#include <stack>
#include <vector>

using std::cout; 


int contains(char to_check, std::string to_check_list) {
  for (unsigned int i = 0; i < to_check_list.size(); i++) {
    if (to_check_list[i] == to_check) {
      return i;
    }
  }
  return -1;
}


bool Check(std::string all_braces) {
  std::string open_braces = "[({";
  std::string close_braces = "])}";

  std::stack<char> element_stack;

  for (unsigned int i = 0; i < all_braces.size(); i++) {
    int value = contains(all_braces[i], open_braces);
    if (value != -1) {
      element_stack.push(all_braces[i]);
    } 
    else {
      unsigned int end_index = contains(all_braces[i], close_braces);
      char open_braces_element = open_braces[end_index];
    
      if (element_stack.top() == open_braces_element) 
      {
        element_stack.pop();
        continue;
      } else {
        return false;
      }
    }
  }
  return true;
}


std::string Convert(bool result){
    if(result){
        cout <<"Correct sequence";
    }
    else{
        cout<< "Wrong braces";        
    }
}


int main() {
    
    std::string str;
    std::cout <<"Your braces: ";
    std:: cin >> str;
    bool result = Check(str);
  
    Convert(result) ;
  return 0;
}
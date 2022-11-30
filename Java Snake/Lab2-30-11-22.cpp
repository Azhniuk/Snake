#include <iostream>
#include <stack>
#include <vector>

using std::cout; using std::endl;
int contains(char to_check, std::string to_check_list) {
  for (unsigned int i = 0; i < to_check_list.size(); i++) {
    if (to_check_list[i] == to_check) {
      return i;
    }
  }
  return -1;
}

bool bracesCheck(std::string all_braces) {
  std::string open_braces = "[({";
  std::string close_braces = "])}";

  std::stack<char> element_stack;

  for (unsigned int i = 0; i < all_braces.size(); i++) {
    int value = contains(all_braces[i], open_braces);
    if (value != -1) {
      element_stack.push(all_braces[i]);
      std::cout << "Pushed: " << all_braces[i] << std::endl;
    } else {
      unsigned int end_index = contains(all_braces[i], close_braces);
      char open_braces_element = open_braces[end_index];
      std::cout << "Popped: " << element_stack.top() << " for across " << all_braces[i] << std::endl;
      if (element_stack.top() == open_braces_element) {
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
  std::string list_of_braces = "({[()]})";
  bool result = bracesCheck(list_of_braces);
  
  std::cout << "Result: " << Convert(result) ;
  return 0;
}
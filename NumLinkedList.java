/**
 * 
 */
import java.util.NoSuchElementException;

/**
 * @author Emmanuel Makoye
 * A NumLinkedList class that implements a NumList interface
 *
 */
public class NumLinkedList implements NumList {
  
  
  //a field to store the front node 
  private DLNode front;
  //a field to store the back node 
  private DLNode back;
  // a field to store the size  
  private int size;
  // a field to store the capacity 
  private int capacity;
  // a field to keep track of whether the list is sorted or not
  public boolean sorted;
  
  // A constructor that creates a list with capacity of 15
  public NumLinkedList() {
    capacity = 100000;
    size = 0;
    sorted = true;
    front = back = null;
  }
  
  /**
   * returns the number of elements present in NumLinkedList
   * @return size
   */
  @Override
  public int size() {
    return size;
  }
  
  /**
   * returns the number of elements the list can hold
   * @return an int that is the capacity 
   */
  @Override
  public int capacity() {
    return capacity;
  }
  
  /**
   * checks whether the list is sorted or not
   * @return true if sorted and false otherwise
   */
  public boolean isSorted(){
    return sorted;
  }
  
  
  
  /**
   * adds a value to the list while increasing the size
   * also checks to see if the list is sorted or not
   */
  @Override    
  public void add(double d) {
    if(size() != 0){
      //checks if the added value breaks the order or not
      if(getBack().getElement() > d && sorted == true ){
        sorted = false;
      }
    }
    addToBack(d);
    size++;
  }
  
  /**
   * inserts a value at a specified index in the list
   */
  @Override
  public void insert(int i, double value) {
    if(size() == 0){
      add(value);
    }
    else{
    if(i == 0){
      //checks if the insert value breaks the order or not
      if(getFront().getElement() < value && sorted == true ){
        sorted = false;
      }
      addToFront(value);
      size++;
    }
    else if(i >= size()){
      //checks if the inserted value breaks the order or not
      if(getBack().getElement() > value && sorted == true ){
        sorted = false;
      }
      addToBack(value);
      size++;
    }
    else if (i > 0 && i + 1 < size()){
      DLNode ptr = getFront();
      //keeps track of elements' indexes as we loop around
      int counter = 0;
      //loops around to find the specific spot to insert the element
      while(ptr != null){
        if(counter == i){
          //compares the value with bordering elements to see if order is preserved
          if((ptr.getElement() < value || ptr.getPrevious().getElement() > value) && sorted == true ){
            sorted = false;
          }
          size++;
          DLNode temp = new DLNode(value, ptr.getPrevious(), ptr);
        }
        counter++;
        ptr = ptr.getNext();
      }
    }
    }
  }
  
  /**
   * removes an a double value at the specified index
   */
  @Override
  public void remove(int i) {
    //removing from an empty list
    if(isEmpty()){
      throw new NoSuchElementException();
    }
    //removing the first element
    else{
      if(i == 0){
        removeFromFront();
        size--;
      }
      //trying to remove at an index that's beyond the last element
      else if(i + 1 > size()){
        throw new NoSuchElementException();
      }
      //removing the last element
      else if( i + 1 == size()){
        removeFromBack();
        size--;
      }
      else if(i != 0 && i + 1 < size()){
        DLNode ptr = getFront();
        //keeps track of elements' indexes as we loop around
        int counter = 0;
        //loops around to find the element to be removed
        while(ptr != null){
          if(counter == i){
            setBack(new DLNode (ptr.getNext().getElement(), ptr.getPrevious(), ptr.getNext().getNext()));
            size--;
          }
          counter++;
          ptr = ptr.getNext();
        }
      }
      
    }
  }
  
  /**
   * checks if the list contains the parameter value 
   */
  @Override
  public boolean contains(double value) {
    //checking an empty list
    if(isEmpty())
      return false;
    else{
      DLNode ptr = getFront();
      while(ptr != null){
        //compares every element to the value
        if(value == ptr.getElement()){
          return true;
        }
        ptr = ptr.getNext();
      }
    }
    return false;
  }
  
  /**
   * checks what double value is at a specific index
   * @return a double value at that index
   */
  @Override
  public double lookup(int i) {
    if(i + 1 <= size()){
      DLNode ptr = getFront();
      //keeps track of elements' indexes as we loop around
      int counter = 0;
      while(ptr != null){
        if(counter == i){
          return ptr.getElement();
        }
        ptr = ptr.getNext();
        counter++;
      }
    }
    //looking for an element beyond the index of the last element
    throw new NoSuchElementException();
  }
  
  /**
   * checks whether a specific NumList is equal to this list
   * @return true only if the list is exactly equal
   */
  @Override
  public boolean equals(NumList otherList) {
    //for different sizes, false is returned immediately
    if(this.size() != otherList.size())
      return false;
    else {
      //the NumLists are changed to a String for easy comparison
      String str1 = this.toString();
      String str2 = otherList.toString();
      //takes advantage of the String compareTo method
      if(str1.compareTo(str2) == 0)
        return true;
      else{
        return false;
      }
    }
  }
  
  /**
   * removes any duplicates in the list
   */
  @Override
  public void removeDuplicates() {
    DLNode temp1 = getFront();
    //a list where the non duplicated elements will be put
    NumLinkedList save = new NumLinkedList();
    while(temp1 != null){
      //adding the first element
      if(save.size() == 0){
        save.add(getFront().getElement());
      }
      //adding other elements only if the save list does not contain that element
      if(save.contains(temp1.getElement()) == false){
        save.add(temp1.getElement());
      }
      temp1 = temp1.getNext();
    }
    // "this" NumLinkedList's front node is assigned to save that does not contain duplicates
    front = back = null;
    front = save.getFront();
    back = save.getBack();
  } 
  
  
  /**
   * returns a String representation of the NumLinkedList
   * @return String representation of the list
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    DLNode ptr = getFront();
    while(ptr != null){
      //the first elements are appended with a space in front
      if(ptr.getNext() != null){
        builder.append(ptr.getElement());
        builder.append(' ');
      }
      //the last element is appended without a space in front
      else{
        builder.append(ptr.getElement());
      }
      ptr = ptr.getNext();
    }
    return builder.toString();
  }
  
  
  public void reverse(){
    if(isEmpty() == false){
      //a temporary node holder that holds the list
      DLNode temp1 = getBack();
      //list is emptied before adding the original elements in reverse order
      front = back = null;
      while(temp1 != null){
        addToBack(temp1.getElement());
        temp1 = temp1.getPrevious();
      }
    } 
  }
  
  @Override
  public NumList union(NumList list1, NumList list2){
    //lists are typecasted to this class to be able to access "this" methods
    NumLinkedList firstList = (NumLinkedList)list1;
    NumLinkedList secondList = (NumLinkedList)list2;
    DLNode temp = secondList.getFront();
    while(temp != null){
      //loops through list2 while adding it's elements to list1
      firstList.add(temp.getElement());
      temp = temp.getNext();
    }
    //checks and removes any duplicate from the union list
    if(firstList.duplicates() == true){
      firstList.removeDuplicates();
    }
    //sorts the union list in ascending order
    firstList.sort();
    return list1;
  }
  
  
  
  public static void main(String[] args) {    
    NumLinkedList list = new NumLinkedList();
    NumLinkedList list2 = new NumLinkedList();
    
    for(int i = 0; i < 10; i++){
      list.add(4.0);
      list.add(8.0);
      list.add(16.0);
      list.add(32.0);
      list.add(5.0);
      list.add(70.0);
    }
    list2.add(30.0);
    list2.add(30.0);
    list2.add(32.0);
    list2.add(39.0);
    list2.add(100.0);
    list2.add(122.0);
    list2.insert(4, 60.0);
    System.out.println("List size: " + list.size());
    System.out.println("List capacity: " + list.capacity());
    System.out.println("String Representation of the list: " + list.toString());
    list.removeDuplicates();
    System.out.println("Duplicates removed: " + list.toString());
    list.reverse();
    System.out.println("The List is reversed: " + list.toString());
    list.add(777.7);
    System.out.println("777.7 is added to the list: " + list.toString());
    list.insert(0, 100.0);
    System.out.println("100.0 is inserted at index 0: " + list.toString());
    list.insert(4, 300.0);
    System.out.println("300.0 is inserted at index 4: " + list.toString());
    System.out.println("returns false because the list is not sorted: " + list.isSorted());
    System.out.println("returns true because the list is sorted: " + list2.isSorted());
    System.out.println("returns false because the two lists are not equal: " + list.equals(list2));
    System.out.println("List is united with list2: " + list2.union(list2, list).toString());
    list2 = list;
    System.out.println("returns true because the two lists are now equal: " + list.equals(list2));
  }
  
  //-----------------------------------------HELPER METHODS-------------------------------------------------// 
  
  //checks wheather there are duplicates in the list
  private boolean duplicates() {
    DLNode temp = getFront();
    while(temp != null){
      DLNode temp2 = temp.getNext();
      while(temp2 != null){
        if(temp.getElement() == temp2.getElement()){
          return true;
        }
        temp2 = temp2.getNext();
      }
      temp = temp.getNext();
    }
    return false;
  }
  
  //orders the list in ascending order
  public void sort(){
    removeDuplicates();
    NumLinkedList save = new NumLinkedList();
    DLNode temp = getFront();
    double holder2 = Double.MIN_VALUE;
    while(temp != null){
      DLNode temp2 = getBack();
      double holder = Double.MAX_VALUE;
      while(temp2 != null){
        if(temp2.getElement() < holder && save.size() == 0){
          holder = temp2.getElement();
        }
        else if(temp2.getElement() < holder && temp2.getElement() > holder2){
          holder = temp2.getElement();    
        }
        temp2 = temp2.getPrevious();
      }
      save.add(holder);
      holder2 = holder;
      temp = temp.getNext();
    }
    front = save.getFront();
  }
  
  // returns the front node of the list
  private DLNode getFront(){
    return front;
  }
  
  // returns the back node of the list
  private DLNode getBack(){
    return back;
  }
  
  //checks wheather the list is empty or not
  private boolean isEmpty(){
    return getFront() == null;
  }
  
  //add an element to the front of the list
  private void addToFront(Double element){
    setFront(new DLNode(element, null, getFront()));
    if (getBack() == null){
      setBack(getFront());
    }
  }
  
  //sets the back node 
  private void setBack(DLNode node) {
    back = node;
  }
  
  //sets the front node
  private void setFront(DLNode node){
    front = node;
  }
  
  //adds an element to the back of the list
  private void addToBack(double element) {
    setBack(new DLNode(element, getBack(), null));
    if (getFront() == null){
      setFront(getBack());
    }
  }
  
  //revomes one element from the back of the list
  private void removeFromBack(){
    if(size() == 0){
      throw new NoSuchElementException();
    }
    else if(size() == 1){
      front = back = null;
    }
    else if(size() > 1){
      DLNode temp = getFront();
      front = back = null;
      size = 0;
      while(temp.getNext() != null){
        add(temp.getElement());
        temp = temp.getNext();
      }
    }
  }
  
  //removes one element from the front of the list
  private void removeFromFront(){
    if(size() == 1){
      front = back = null;
    }
    else{
      setFront(new DLNode(getFront().getNext().getElement(), null, getFront().getNext().getNext()));
    }
  }
  
}



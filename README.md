Student Management System application::

Feedback from peer after testing::

1.- What happens if student.txt is not created and the program try to read it... will this crash... how the handles it?
2.- Sorting function seems complex for a simple re-arrange function... is there a way to simplify it make the code more effective?
3.- The input to add a new student yes or no... could be more user friendly so the user know exactly what to type

-----------------------------------------------------------------------------------------------------------------------------------

Analysis of feedback and changes

1.- After testing we werent able to reproduce this scenario... however there was a try-catch error handler for this scenario in place...
if the file student.txt does not get produced the user should see an error message specifying that the file wasnt created...
"The file 'students.txt' was not found".

2.- the sorting function was considered taking into account the requirement of the assigment... however due to the implementation
and the size of a potential class... bubble sort type of algorithm shouldn't have an issue to handle this numbers.

3.- to add a better user friendly interface will requiered a lot more time and resources that escape the scope of the assigment.
However the instruction comments were updated to explain in a better way exactly what the system is expecting... and also the code was
design in a way that allows the user to use capital letters without incurring in a invalid input...
whit this validations in place and taking into account the scope of the assigment I think the user interaction is simple and straight forwad.





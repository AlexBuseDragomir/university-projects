# Simple Auto Service Application

A simple college Python program that emulates an auto service.

REQUIREMENTS: A non-stop auto service unit has five workstations for maintenance and/or repair work on cars. Each workstation is managed by an auto mechanic and allows for a variety of six types of services (not necessarily the same at all repair stations), each type of service having a predefined execution time.
The operations at the service unit proceed as follows:
- Each car has an arrival time at the service.
- Upon arrival, a work order is completed by the service dispatcher, consisting of the list and sequence of works to be performed; the work order is considered issued at the same time as the car's arrival at the service.
- The works must be performed in the specified order on the work order.
- If there is an available workstation for the first job, the car is placed at that workstation and the work is carried out; otherwise, the car is added to a waiting list.
- After completing the operations, the car is removed from the workstation and re-entered into the waiting list, or it exits the unit, depending on the number of works that still need to be performed.
- The waiting list is used to determine the first car to be transferred to an available workstation.
- Write a rule base that allows for the management and coordination of the service unit's activities, as well as the following reports:
    - Utilization report for each workstation, specifying the total working and waiting time for that workstation; this report will be generated upon request.
    - Report for the processing of each vehicle: arrival time, completion time of works, waiting time, and processing time of the works; this report will be generated upon each car's exit from the service.
 
For each car entering the service, the following must be specified: registration number, car type, entry time, and work order.
Write an object-oriented Python application to simulate the auto service unit. The program should attempt to optimize the service unit's working time by processing as many cars as possible in a day.

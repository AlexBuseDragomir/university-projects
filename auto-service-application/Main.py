# DISCLAIMER: All names and license plates used in this simulation are fictional.
# Any resemblance to real persons or vehicles is purely coincidental.

# we import all class definitions and functions from the module
from Module import *

# we create some random cars with different problems we set their arrival time at the service
# we fill the car report with the license plate, the car type, the work order and the car report (empty at first)

work_order1 = [("change wheel", (0, 30, 0)), ("repair engine", (2, 45, 0))]
car_report1 = [0, 0, 0, 0, 0]
car1 = Car("PLATE-01", "Sedan", (8, 5, 0), work_order1, car_report1)

work_order2 = [("inflate wheel", (0, 15, 0)), ("change oil", (0, 45, 0))]
car_report2 = [0, 0, 0, 0, 0]
car2 = Car("PLATE-02", "SUV", (9, 15, 0), work_order2, car_report2)

work_order3 = [("paint/repaint car", (3, 30, 0)), ("repair distribution", (2, 30, 0))]
car_report3 = [0, 0, 0, 0, 0]
car3 = Car("PLATE-03", "Hatchback", (8, 50, 0), work_order3, car_report3)

work_order4 = [("repair direction", (1, 30, 0)), ("change wheel", (0, 30, 0))]
car_report4 = [0, 0, 0, 0, 0]
car4 = Car("PLATE-04", "Pickup truck", (9, 0, 0), work_order4, car_report4)

work_order5 = [("repair cooling system", (1, 10, 0)), ("change battery", (0, 20, 0))]
car_report5 = [0, 0, 0, 0, 0]
car5 = Car("PLATE-05", "Sedan", (9, 20, 0), work_order5, car_report5)

work_order6 = [("repair exhaust system", (1, 30, 0)), ("repair engine", (2, 45, 0))]
car_report6 = [0, 0, 0, 0, 0]
car6 = Car("PLATE-06", "Convertible", (9, 35, 0), work_order6, car_report6)

work_order7 = [("repair direction", (1, 30, 0)), ("repair braking system", (2, 0, 0))]
car_report7 = [0, 0, 0, 0, 0]
car7 = Car("PLATE-07", "Minivan", (10, 25, 0), work_order7, car_report7)

work_order8 = [("repair cooling system", (1, 10, 0)), ("inflate wheel", (0, 15, 0))]
car_report8 = [0, 0, 0, 0, 0]
car8 = Car("PLATE-08", "Sports car", (10, 55, 0), work_order8, car_report8)

work_order9 = [("change oil", (0, 45, 0)), ("repair distribution", (2, 30, 0))]
car_report9 = [0, 0, 0, 0, 0]
car9 = Car("PLATE-09", "Hatchback", (10, 21, 0), work_order9, car_report9)

work_order10 = [("change battery", (0, 20, 0)), ("paint/repaint car", (3, 30, 0))]
car_report10 = [0, 0, 0, 0, 0]
car10 = Car("PLATE-10", "Truck", (10, 56, 0), work_order10, car_report10)

# we assume that each service will have available the full list of services
list_of_services1 = [("change wheel", (0, 30, 0)), ("inflate wheel", (0, 15, 0)), ("repair engine", (2, 45, 0))]

list_of_services2 = [("change oil", (0, 45, 0)), ("paint/repaint car", (3, 30, 0)), ("change battery", (0, 20, 0))]

list_of_services3 = [("repair braking system", (2, 0, 0)), ("repair exhaust system", (1, 30, 0))]

list_of_services4 = [("repair direction", (1, 30, 0)), ("repair cooling system", (1, 10, 0)),
                     ("repair distribution", (2, 30, 0))]

# we concatenate the above four lists in order to obtain the list with all the available services
services = list_of_services1 + list_of_services2 + list_of_services3 + list_of_services4

# now we initialise the repair units of the auto service (we have 5 repair units)
# initially the car queue will be empty (no cars have arrived yet)
car_queue = []
# initially there is no car in the service unit
current_car = Car()
# initially there is no utilisation report available
utilisation_report = [(0, 0, 0), (0, 0, 0)]

unit1 = RepairUnit("Mechanic 1", services, car_queue, current_car, utilisation_report)
unit2 = RepairUnit("Mechanic 2", services, car_queue, current_car, utilisation_report)
unit3 = RepairUnit("Mechanic 3", services, car_queue, current_car, utilisation_report)
unit4 = RepairUnit("Mechanic 4", services, car_queue, current_car, utilisation_report)
unit5 = RepairUnit("Mechanic 5", services, car_queue, current_car, utilisation_report)

# we create a list containing all cars
car_list = [car1, car2, car3, car4, car5, car6, car7, car8, car9, car10]
# we will use auxiliary for printing the cars and information about them
auxiliary_car_list = [car1, car2, car3, car4, car5, car6, car7, car8, car9, car10]

# we create a list containing all service units
repair_unit_list = [unit1, unit2, unit3, unit4, unit5]
# we will use auxiliary for printing the cars and information about them
auxiliary_unit_list = [unit1, unit2, unit3, unit4, unit5]

# we instantiate 'ProcessingCars' with today's list of cars and service units
today_reports = ProcessingCars(car_list, repair_unit_list)

# we assign the cars of today's list into the corresponding service unit and create the reports
today_reports.assign_cars()

index = 1

# we will print information about our cars
for car in auxiliary_car_list:

    print("DATA ABOUT THE CAR ", end = "")
    print(index, end = "")
    print(" : ")
    index += 1
    print("Car type : ", end = "")
    print(car.car_type)
    print("Car license plate : ", end = "")
    print(car.license_plate)
    print("Name of the mechanic : ", end = "")
    print(car.car_report[0])
    print("Arrival hour : ", end="")
    print(car.car_report[1])
    print("Leaving time : ", end="")
    print(car.car_report[2])
    print("Waiting time : ", end="")
    print(car.car_report[3])
    print("Total time spent in service : ", end="")
    print(car.car_report[4])
    print()
    print()

index = 1

# we will print information about the service units
for unit in auxiliary_unit_list:
    print("DATA ABOUT THE SERVICE UNIT ", end="")
    print(index, end="")
    print(" : ")
    index += 1
    print("Total working time : ", end = "")
    print(unit.utilisation_report[0])
    print("Total waiting time : ", end="")
    print(unit.utilisation_report[1])
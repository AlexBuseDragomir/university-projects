# function that takes two time tuples and returns a tuple containing the difference in hours, minutes and seconds
def convert_time_difference(time1, time2):
    # we calculate the difference as a tuple of form (dif_hours, dif_minutes, dif_seconds)
    seconds_difference = (time1[0] * 3600 + time1[1] * 60 + time1[2]) - (time2[0] * 3600 + time2[1] * 60 + time2[2])
    hours = seconds_difference // 3600
    seconds_difference = seconds_difference - hours * 3600
    minutes = seconds_difference // 60
    seconds = seconds_difference - minutes * 60
    result_time = (hours, minutes, seconds)

    return result_time


# function that takes two time tuples and returns a tuple containing the sum in hours, minutes and seconds
def convert_time_sum(time1, time2):
    # we calculate the sum as a tuple of form (sum_hours, sum_minutes, sum_seconds)
    seconds_sum = (time1[0] * 3600 + time1[1] * 60 + time1[2]) + (time2[0] * 3600 + time2[1] * 60 + time2[2])
    hours = seconds_sum // 3600
    seconds_sum = seconds_sum - hours * 3600
    minutes = seconds_sum // 60
    seconds = seconds_sum - minutes * 60
    result_time = (hours, minutes, seconds)

    return result_time


# function that compares two time tuples and returns TRUE if time1 <= time2
def less_than(time1, time2):
    # transforming the time into seconds to make the compare easier
    seconds1 = time1[0] * 3600 + time1[1] * 60 + time1[2]
    seconds2 = time2[0] * 3600 + time2[1] * 60 + time2[2]

    if seconds1 <= seconds2:
        return True

    return False


# function that gets the time needed in order to fix the current car
def service_time(my_car):
    # we set the spent time to (0, 0, 0)
    spent_time = (0, 0, 0)

    # for each task in the list of services needed, we add it to the spent_time tuple
    for elem in my_car.work_order:
        spent_time = convert_time_sum(spent_time, elem[1])

    # we return a time tuple of form (hh, mm, ss) representing the time that the car must spend in the service
    return spent_time


# function that assigns a car to a unit that has nothing to do at the moment
def assign_car_to_empty_unit(my_car, my_unit):

    # adding the new car as the current car in the service
    my_unit.current_car = my_car

    # the queue is empty because the first car will be processed immediately
    my_unit.car_queue = []
    my_car.car_report[0] = my_unit.mechanic

    # we set the arrival time of the car in the service
    my_car.car_report[1] = my_car.arrival_time

    # we get the time spent in the service
    time_in_service = service_time(my_car)

    # the leaving time is equal with the sum of the arrival time and the processing time
    leaving_time = convert_time_sum(my_car.arrival_time, time_in_service)
    my_car.car_report[2] = leaving_time

    # the waiting time will be 0
    waiting_time = (0, 0, 0)
    my_car.car_report[3] = waiting_time

    # the total time spent in service will be the sum of the waiting time and the service time (active time)
    # it can also be calculated as the difference between the leaving time and the arrival time
    total_time_spent = convert_time_sum(waiting_time, time_in_service)
    my_car.car_report[4] = total_time_spent

    # total working time will be equal with the sum of the actual time plus the service_time of the actual car
    total_working_time = my_unit.utilisation_report[0]
    total_working_time = convert_time_sum(total_working_time, time_in_service)

    # the total waiting time remains unchanged
    total_waiting_time = my_unit.utilisation_report[1]
    my_unit.utilisation_report = [total_working_time, total_waiting_time]

    # we will return a tuple containing the modified data
    my_result = (my_car, my_unit)

    return my_result


# function that assigns a car to a unit that is working on a car at that moment
def assign_car_to_used_unit(my_car, my_unit):

    # we put the new car in the queue
    my_unit.car_queue.append(my_car)
    my_car.car_report[0] = my_unit.mechanic

    # we set the arrival time of the car in the service
    my_car.car_report[1] = my_car.arrival_time

    # we get the time the car must spend in the service
    time_in_service = service_time(my_car)

    # the waiting time will be the difference between the other car's finishing time and this car's arrival time
    last_car_leaving = my_unit.current_car.car_report[2]
    waiting_time = convert_time_difference(last_car_leaving, my_car.arrival_time)
    my_car.car_report[3] = waiting_time

    # the total time spent in service will be the sum of the waiting time and the service time (active time)
    # it can also be calculated as the difference between the leaving time and the arrival time
    total_time_spent = convert_time_sum(waiting_time, time_in_service)
    my_car.car_report[4] = total_time_spent

    # the leaving time is equal with the sum of the arrival time, the waiting time and the processing time
    # the leaving time is equal with the sum of the arrival time and the total time spent in the service
    leaving_time = convert_time_sum(my_car.arrival_time, total_time_spent)
    my_car.car_report[2] = leaving_time

    # total working time will be equal with the sum of the actual time plus the service_time of the actual car
    total_working_time = my_unit.utilisation_report[0]
    total_waiting_time = my_unit.utilisation_report[1]
    total_working_time = convert_time_sum(total_working_time, time_in_service)
    total_waiting_time = convert_time_sum(total_waiting_time, waiting_time)
    my_unit.utilisation_report = [total_working_time, total_waiting_time]

    # we put the new car out of the queue and add it to the current_car position
    my_unit.car_queue.pop()
    my_unit.current_car = my_car

    # we will return a tuple containing the modified data
    my_result = (my_car, my_unit)

    return my_result


# function that calculates the waiting time for a specified service unit and a new car
def calculate_waiting_time(my_unit, my_car):
    new_car_arrival = my_car.arrival_time
    last_car = my_unit.current_car
    last_car_leaving = last_car.car_report[2]
    waiting_time = convert_time_difference(last_car_leaving, new_car_arrival)

    return waiting_time


# will contain all the important data regarding an automobile
class Car(object):

    # we initialise the data fields of a car
    def __init__(self, license_plate = "no license plate", car_type = "unknown type",
                 arrival_time = (-1, -1, -1), work_order = None, car_report = None):

        # the license plate will contain a string
        self.license_plate = license_plate

        # the car type (SUV, truck, hatchback, sedan etc.)
        self.car_type = car_type

        # the arrival time will be seen as a tuple with three values
        # first for the hour, the second for the minute and the last for seconds
        self.arrival_time = arrival_time

        # the work order will be a list of tuples
        # the first value in the tuples is the task that must be carried out
        # the second value is the time needed in order to complete the task
        # [(string : the task, tuple : time needed), ...] -> the tuples are of form (the_task, (hh, mm, ss))
        # we assumed that a consumer knows the time needed in order to complete a repair task
        self.work_order = work_order

        # this will contain information about the operations done on the car
        # that was in the service (the car that is leaving the service now)
        # a list of form [service_unit, arrival_time, leaving_time, waiting_time, time_spent_in_service)]
        self.car_report = car_report


# will contain all the important data regarding the repair units
# our auto service contains 5 repair units
class RepairUnit(object):

    # we initialise the data fields of a repair unit
    def __init__(self, mechanic = "", services = None, car_queue = None,
                 current_car = None, utilisation_report = None):

        # the name of the mechanic assigned to the specific repair unit
        self.mechanic = mechanic

        # the list of services offered and the time required in order to complete them
        # we assume that each service unit can perform all services on the list
        # it is a list of tuples of form [(service_name, (hh, mm, ss))]
        # where (hh, mm, ss) represents the time needed in order to complete a task
        self.services = services

        # the list of cars that wait for one or more services
        self.car_queue = car_queue

        # this will contain no element if there is no car in the service
        # or an instance of the car class if there is a car in the service
        self.current_car = current_car

        # this will contain a list containing at a certain moment
        # the total working time and the total waiting time
        # list of form [total working time after this car, total waiting time after this car]
        # total_working_time and total_waiting_time are of form (hh, mm, ss)
        self.utilisation_report = utilisation_report


class ProcessingCars(object):

    # we will initialise the lists of cars and repair units
    def __init__(self, car_list = None, repair_unit_list = None):

        # this is a list of 'Car' class objects representing the automobiles
        # and the relative order of their arrival at the service
        self.car_list = car_list

        # this is a list of 'RepairUnit' class objects representing the repair units
        self.repair_unit_list = repair_unit_list

    # this method will assign cars and write reports
    def assign_cars(self):

        # for the first five cars it is easy
        # they are each assigned to one of the five repairs units
        # we will save the instances of the class 'RepairUnit' in unit 1,2,3,4,5
        car1 = self.car_list[0]
        car2 = self.car_list[1]
        car3 = self.car_list[2]
        car4 = self.car_list[3]
        car5 = self.car_list[4]

        unit1 = self.repair_unit_list[0]
        unit2 = self.repair_unit_list[1]
        unit3 = self.repair_unit_list[2]
        unit4 = self.repair_unit_list[3]
        unit5 = self.repair_unit_list[4]

        # we will assign a car for each of the five repair units
        # we set the queue to empty for each repair unit
        # we update the utilisation report for the first unit of the day
        # after a car gets put in a repair unit, it will be erased from the list of incoming automobiles

        # set the new current car to car1
        unit1.current_car = car1

        # the queue is empty because the first car will be processed immediately
        unit1.car_queue = []
        car1.car_report[0] = unit1.mechanic

        # we set the arrival time of the car in the service
        car1.car_report[1] = car1.arrival_time

        # we get the time spent in the service
        time_in_service = service_time(car1)

        # the leaving time is equal with the sum of the arrival time and the processing time
        leaving_time = convert_time_sum(car1.arrival_time, time_in_service)
        car1.car_report[2] = leaving_time

        # the waiting time will be 0
        waiting_time = (0, 0, 0)
        car1.car_report[3] = waiting_time

        # the total time spent in service will be the sum of the waiting time and the service time (active time)
        # it can also be calculated as the difference between the leaving time and the arrival time
        total_time_spent = convert_time_sum(waiting_time, time_in_service)
        car1.car_report[4] = total_time_spent

        # we delete the car from the list of today's vehicles
        del self.car_list[0]

        # total working time will be equal with the sum of the actual time plus the service_time of the actual car
        total_working_time = (0, 0, 0)
        total_working_time = convert_time_sum(total_working_time, time_in_service)

        # first five cars don't have to wait
        total_waiting_time = (0, 0, 0)
        unit1.utilisation_report = [total_working_time, total_waiting_time]


        # set the new current car to car2
        unit2.current_car = car2

        # the queue is empty because the first car will be processed immediately
        unit2.car_queue = []
        car2.car_report[0] = unit2.mechanic

        # we set the arrival time of the car in the service
        car2.car_report[1] = car2.arrival_time

        # we get the time spent in the service
        time_in_service = service_time(car2)

        # the leaving time is equal with the sum of the arrival time and the processing time
        leaving_time  = convert_time_sum(car2.arrival_time, time_in_service)
        car2.car_report[2] = leaving_time

        # the waiting time will be 0
        waiting_time = (0, 0, 0)
        car2.car_report[3] = waiting_time

        # the total time spent in service will be the sum of the waiting time and the service time (active time)
        # it can also be calculated as the difference between the leaving time and the arrival time
        total_time_spent = convert_time_sum(waiting_time, time_in_service)
        car2.car_report[4] = total_time_spent

        # we delete the car from the list of today's vehicles
        del self.car_list[0]

        # total working time will be equal with the sum of the actual time plus the service_time of the actual car
        total_working_time = (0, 0, 0)
        total_working_time = convert_time_sum(total_working_time, time_in_service)

        # first five cars don't have to wait
        total_waiting_time = (0, 0, 0)
        unit2.utilisation_report = [total_working_time, total_waiting_time]


        # set the new current car to car3
        unit3.current_car = car3

        # the queue is empty because the first car will be processed immediately
        unit3.car_queue = []
        car3.car_report[0] = unit3.mechanic

        # we set the arrival time of the car in the service
        car3.car_report[1] = car3.arrival_time

        # we get the time spent in the service
        time_in_service = service_time(car3)

        # the leaving time is equal with the sum of the arrival time and the processing time
        leaving_time = convert_time_sum(car3.arrival_time, time_in_service)
        car3.car_report[2] = leaving_time

        # the waiting time will be 0
        waiting_time = (0, 0, 0)
        car3.car_report[3] = waiting_time

        # the total time spent in service will be the sum of the waiting time and the service time (active time)
        # it can also be calculated as the difference between the leaving time and the arrival time
        total_time_spent = convert_time_sum(waiting_time, time_in_service)
        car3.car_report[4] = total_time_spent

        # we delete the car from the list of today's vehicles
        del self.car_list[0]

        # total working time will be equal with the sum of the actual time plus the service_time of the actual car
        total_working_time = (0, 0, 0)
        total_working_time = convert_time_sum(total_working_time, time_in_service)

        # first five cars don't have to wait
        total_waiting_time = (0, 0, 0)
        unit3.utilisation_report = [total_working_time, total_waiting_time]


        # set the new current car to car4
        unit4.current_car = car4

        # the queue is empty because the first car will be processed immediately
        unit4.car_queue = []
        car4.car_report[0] = unit4.mechanic

        # we set the arrival time of the car in the service
        car4.car_report[1] = car4.arrival_time

        # we get the time spent in the service
        time_in_service = service_time(car4)

        # the leaving time is equal with the sum of the arrival time and the processing time
        leaving_time = convert_time_sum(car4.arrival_time, time_in_service)
        car4.car_report[2] = leaving_time

        # the waiting time will be 0
        waiting_time = (0, 0, 0)
        car4.car_report[3] = waiting_time

        # the total time spent in service will be the sum of the waiting time and the service time (active time)
        # it can also be calculated as the difference between the leaving time and the arrival time
        total_time_spent = convert_time_sum(waiting_time, time_in_service)
        car4.car_report[4] = total_time_spent

        # we delete the car from the list of today's vehicles
        del self.car_list[0]

        # total working time will be equal with the sum of the actual time plus the service_time of the actual car
        total_working_time = (0, 0, 0)
        total_working_time = convert_time_sum(total_working_time, time_in_service)

        # first five cars don't have to wait
        total_waiting_time = (0, 0, 0)
        unit4.utilisation_report = [total_working_time, total_waiting_time]


        # set the current car to car5
        unit5.current_car = car5

        # the queue is empty because the first car will be processed immediately
        unit5.car_queue = []
        car5.car_report[0] = unit5.mechanic

        # we set the arrival time of the car in the service
        car5.car_report[1] = car5.arrival_time

        # we get the time spent in the service
        time_in_service = service_time(car5)

        # the leaving time is equal with the sum of the arrival time and the processing time
        leaving_time = convert_time_sum(car5.arrival_time, time_in_service)
        car5.car_report[2] = leaving_time

        # the waiting time will be 0
        waiting_time = (0, 0, 0)
        car5.car_report[3] = waiting_time

        # the total time spent in service will be the sum of the waiting time and the service time (active time)
        # it can also be calculated as the difference between the leaving time and the arrival time
        total_time_spent = convert_time_sum(waiting_time, time_in_service)
        car5.car_report[4] = total_time_spent

        # we delete the car from the list of today's vehicles
        del self.car_list[0]

        # total working time will be equal with the sum of the actual time plus the service_time of the actual car
        total_working_time = (0, 0, 0)
        total_working_time = convert_time_sum(total_working_time, time_in_service)

        # first five cars don't have to wait
        total_waiting_time = (0, 0, 0)
        unit5.utilisation_report = [total_working_time, total_waiting_time]

        # for each remaining car in the list of automobiles we put it in a repair unit
        for new_car in self.car_list :

            last_car1 = unit1.current_car
            last_car2 = unit2.current_car
            last_car3 = unit3.current_car
            last_car4 = unit4.current_car
            last_car5 = unit5.current_car

            # if by the time the new car comes the service unit 1 is empty
            if less_than(last_car1.car_report[2], new_car.arrival_time):
                assign_car_to_empty_unit(new_car, unit1)

            # if by the time the new car comes the service unit 2 is empty
            elif less_than(last_car2.car_report[2], new_car.arrival_time):
                assign_car_to_empty_unit(new_car, unit2)

            # if by the time the new car comes the service unit 3 is empty
            elif less_than(last_car3.car_report[2], new_car.arrival_time):
                assign_car_to_empty_unit(new_car, unit3)

            # if by the time the new car comes the service unit 4 is empty
            elif less_than(last_car4.car_report[2], new_car.arrival_time):
                assign_car_to_empty_unit(new_car, unit4)

            # if by the time the new car comes the service unit 5 is empty
            elif less_than(last_car5.car_report[2], new_car.arrival_time):
                assign_car_to_empty_unit(new_car, unit4)

            # if there is no empty service unit we will search for the one with the smallest waiting time
            else:
                # the waiting time is calculated by doing the difference between the last car's leaving time
                # and the current car's arriving time
                # we calculate the waiting time for each unit

                waiting_time1 = calculate_waiting_time(unit1, new_car)
                waiting_time2 = calculate_waiting_time(unit2, new_car)
                waiting_time3 = calculate_waiting_time(unit3, new_car)
                waiting_time4 = calculate_waiting_time(unit4, new_car)
                waiting_time5 = calculate_waiting_time(unit5, new_car)

                # we add the waiting time into a list and sort it to obtain the best (lowest) one
                waiting_list = [waiting_time1, waiting_time2, waiting_time3, waiting_time4, waiting_time5]
                waiting_list.sort()

                # we put the car in the service unit 1
                if waiting_list[0] == waiting_time1:
                    assign_car_to_used_unit(new_car, unit1)

                # we put the car in the service unit 2
                elif waiting_list[0] == waiting_time2:
                    assign_car_to_used_unit(new_car, unit2)

                # we put the car in the service unit 3
                elif waiting_list[0] == waiting_time3:
                    assign_car_to_used_unit(new_car, unit3)

                # we put the car in the service unit 4
                elif waiting_list[0] == waiting_time4:
                    assign_car_to_used_unit(new_car, unit4)

                # we put the car in the service unit 5
                elif waiting_list[0] == waiting_time5:
                    assign_car_to_used_unit(new_car, unit5)












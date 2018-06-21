# spc_project

http://cs.telhai.ac.il/phpmyadmin/

<classpathentry combineaccessrules="false" kind="src" path="/OCSF"/>

TODO:

Client:
1. in "connectToServer" make connection to server once, and not every time on the "send" method.

Server:


Function list:
1. admin system:
String registerDefectSpot(parkingLot, spot)
String preserveSpot( parkingLot, spot)
String submitUpdatePrices(local, inAdv, sub)
String getPermissionForUpdatePrices(local inAdv, sub)
String produceSnapShot(parkingLot)
String producePerformanceReport
String addNewParkingLot (name, floors, spaces)

2. client system:
String submitInAdvanceParking (id, carId, arrivalDate, arrivalHour, depDate,depHour, parkingLot)
String payInAdvanceParking(id, costInAdvanceFromServer)
String submitSubscription(id, carId, date, regOrBuis)
String paySubscription(id, costSubscriptionFromServer)
String cancelOrder(id, carId, date, hour)
String viewOrder(id, carId)
String complaint(id)

3. CheckIn CheckOut:
String submitCheckIn(id, carId, dep, email)
String payCheckOut(id, cost)
String leaveParkingLot(id, carId)

4. Login:
String signInAsAdmin(user, pass)
String signIn(user, pass)

5. SignUp: 				//already implemented
String signUp(user, pass);

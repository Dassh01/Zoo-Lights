## ZooLights

###Beatrice, Jack, Henry, Luke

This is a project designed to register customers for ZooLights into a party and calculate the costs for the trip depending on numerous different inputs

**This is what we are working on right now**
 - Birthday formatting and error protection
 - more commands!!!
 - make it look nicer
 - make ticket class
 - documentation :(
 - train
 - guest system

some featured code:

```
public static boolean isWeekend(Date date) {
        final int year = date.getYear(), month = date.getMonth(), day = date.getDay();
        LocalDate localDate = LocalDate.of(year, month, day);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
```

Gang lets get this one finished [^1]

[^1]: Footer test

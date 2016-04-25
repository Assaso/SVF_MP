Set objExcel = CreateObject("Excel.Application")
Set objWorkbook = objExcel.Workbooks.Open("C:\Users\JGALLARDO\IdeaProjects\SVF_MP\src\file\basededatos.xls")

objExcel.Application.Run "basededatos.xls!macro1"
objExcel.ActiveWorkbook.Close

objExcel.Application.Quit
WScript.Quit
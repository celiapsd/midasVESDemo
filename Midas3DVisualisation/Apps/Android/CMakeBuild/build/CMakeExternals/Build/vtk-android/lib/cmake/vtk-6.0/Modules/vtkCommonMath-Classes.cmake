set(vtkCommonMath_CLASSES_LOADED 1)
set(vtkCommonMath_CLASSES "vtkAmoebaMinimizer;vtkFastNumericConversion;vtkFunctionSet;vtkInitialValueProblemSolver;vtkMatrix3x3;vtkMatrix4x4;vtkPolynomialSolversUnivariate;vtkQuaternionInterpolator;vtkRungeKutta2;vtkRungeKutta4;vtkRungeKutta45")

foreach(class ${vtkCommonMath_CLASSES})
  set(vtkCommonMath_CLASS_${class}_EXISTS 1)
endforeach()

set(vtkCommonMath_CLASS_vtkFunctionSet_ABSTRACT 1)
set(vtkCommonMath_CLASS_vtkInitialValueProblemSolver_ABSTRACT 1)




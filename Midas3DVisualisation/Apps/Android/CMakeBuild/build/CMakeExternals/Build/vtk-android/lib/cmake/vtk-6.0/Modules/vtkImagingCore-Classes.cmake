set(vtkImagingCore_CLASSES_LOADED 1)
set(vtkImagingCore_CLASSES "vtkExtractVOI;vtkImageAppendComponents;vtkImageAppend;vtkImageBlend;vtkImageCacheFilter;vtkImageCast;vtkImageChangeInformation;vtkImageClip;vtkImageConstantPad;vtkImageDataStreamer;vtkImageDecomposeFilter;vtkImageDifference;vtkImageExtractComponents;vtkImageFlip;vtkImageIterateFilter;vtkImageMagnify;vtkImageMapToColors;vtkImageMask;vtkImageMirrorPad;vtkImagePadFilter;vtkImagePermute;vtkImageResample;vtkImageReslice;vtkImageResliceToColors;vtkImageShiftScale;vtkImageShrink3D;vtkImageThreshold;vtkImageTranslateExtent;vtkImageWrapPad;vtkRTAnalyticSource;vtkImageResize;vtkImageBSplineCoefficients;vtkImageStencilData;vtkImageStencilAlgorithm;vtkAbstractImageInterpolator;vtkImageBSplineInterpolator;vtkImageSincInterpolator;vtkImageInterpolator;vtkImageStencilSource")

foreach(class ${vtkImagingCore_CLASSES})
  set(vtkImagingCore_CLASS_${class}_EXISTS 1)
endforeach()

set(vtkImagingCore_CLASS_vtkImageDecomposeFilter_ABSTRACT 1)
set(vtkImagingCore_CLASS_vtkImageIterateFilter_ABSTRACT 1)
set(vtkImagingCore_CLASS_vtkImagePadFilter_ABSTRACT 1)
set(vtkImagingCore_CLASS_vtkAbstractImageInterpolator_ABSTRACT 1)
set(vtkImagingCore_CLASS_vtkImageBSplineInternals_ABSTRACT 1)



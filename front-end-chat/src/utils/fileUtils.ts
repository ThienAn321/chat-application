const getType = (url: string): string | undefined => {
  const cleanUrl = url.split('?')[0];
  return cleanUrl.split('.').pop()?.toLowerCase();
};

const checkIsFile = (url: string): string => {
  const extension = getType(url);
  switch (extension) {
    case 'pdf':
    case 'doc':
    case 'docx':
    case 'xls':
    case 'xlsx':
      return 'file';
    default:
      return '';
  }
};

const getFileTypeFromUrl = (url: string): string => {
  const extension = getType(url);
  switch (extension) {
    case 'pdf':
      return './pdf-icon.png';
    case 'doc':
    case 'docx':
      return './word-icon.png';
    case 'xls':
    case 'xlsx':
      return './excel-icon.png';
    default:
      return url;
  }
};

const previewFile = (url: string): string | void => {
  const extension = getType(url);
  console.log(extension);
  switch (extension) {
    case 'pdf':
    case 'doc':
    case 'docx':
    case 'xls':
    case 'xlsx':
      window.open(url, '_blank');
      return;
    default:
      return url;
  }
};

const getFileName = (url: string): string => {
  const cleanUrl = url.split('?')[0];
  const parts = cleanUrl.split('/');
  const filenameWithPrefix = parts.pop();
  if (filenameWithPrefix) {
    const decodedFilename = decodeURIComponent(filenameWithPrefix).replace(
      /^images\//,
      ''
    );
    const maxLength = 43;

    if (decodedFilename.length > maxLength) {
      return decodedFilename.substring(0, maxLength - 1) + '...';
    }

    return decodedFilename;
  }

  return '';
};

export { checkIsFile, getFileTypeFromUrl, previewFile, getFileName };

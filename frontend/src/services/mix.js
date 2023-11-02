import axios from 'axios';


export const fetchData = async (url) => {
    
  try {
    const response = await axios.get(url);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching data');
  }
};

export const deleteData = async (url) => {
  try {
    const response = await axios.delete(url);
    return response.data;
  } catch (error) {
    throw new Error('Error deleting data');
  }
};
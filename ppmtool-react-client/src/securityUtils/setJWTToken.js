import axios from 'axios';

const setJWTToken = token => {
  if (token) {
    axios.defaults.hearders.common['Authorization'] = token;
  } else {
    delete axios.defaults.hearders.common['Authorization'];
  }
};

export default setJWTToken;

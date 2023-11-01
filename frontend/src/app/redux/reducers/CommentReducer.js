// reducer.js
import { ADD_COMMENT, UPDATE_COMMENT, DELETE_COMMENT, FETCH_COMMENTS } from '../../redux/actions/CommentAction';

const initialState = {
  comments: [],
};

const commentReducer = (state = initialState, action) => {
  switch (action.type) {
    case ADD_COMMENT:
      return {
        ...state,
        comments: [...state.comments, action.payload],
      };
    case UPDATE_COMMENT:
      return {
        ...state,
        comments: state.comments.map((comment) =>
          comment.id === action.payload.id ? action.payload : comment
        ),
      };
    case DELETE_COMMENT:
      return {
        ...state,
        comments: state.comments.filter((comment) => comment.id !== action.payload),
      };
    case FETCH_COMMENTS:
      return {
        ...state,
        comments: action.payload,
      };
    default:
      return state;
  }
};

export default commentReducer;

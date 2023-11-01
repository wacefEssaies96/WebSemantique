import { combineReducers } from 'redux';
import EcommerceReducer from './EcommerceReducer';
import NavigationReducer from './NavigationReducer';
import NotificationReducer from './NotificationReducer';
import commentReducer from './CommentReducer';

const RootReducer = combineReducers({
  notifications: NotificationReducer,
  navigations: NavigationReducer,
  ecommerce: EcommerceReducer,
  commentReducer
});

export default RootReducer;

import Loadable from 'app/components/Loadable';
import { lazy } from 'react';
import { authRoles } from '../../auth/authRoles';
import PublicationUser from '../publication/PublicationUser';
import Groupe from '../groupe/Groupe';
import EventList from '../event/EventList';
import AddEvent from '../event/AddEvent';

const Analytics = Loadable(lazy(() => import('./Analytics')));

const dashboardRoutes = [
  { path: '/dashboard/default', element: <Analytics />, auth: authRoles.admin },
  { path: '/pub', element: <PublicationUser /> },
  { path: '/group', element: <Groupe /> },
  { path: '/events', element: <EventList /> },
  { path: '/events/add', element: <AddEvent /> }
];

export default dashboardRoutes;

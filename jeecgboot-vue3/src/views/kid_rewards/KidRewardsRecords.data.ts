import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '用户名',
    align:"center",
    dataIndex: 'userName_dictText'
   },
   {
    title: '获得奖励日期',
    align:"center",
    dataIndex: 'rewardDate'
   },
   {
    title: '奖励所需分数',
    align:"center",
    dataIndex: 'rewardPoint'
   },
   {
    title: '奖励项名称',
    align:"center",
    dataIndex: 'rewardName'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '用户名',
    field: 'userName',
    component: 'JSelectUser',
    componentProps:{
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入用户名!'},
          ];
     },
  },
  {
    label: '奖励项id',
    field: 'rewardId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入奖励项id!'},
          ];
     },
  },
  {
    label: '获得奖励日期',
    field: 'rewardDate',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入获得奖励日期!'},
          ];
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  userName: {title: '用户名',order: 0,view: 'sel_user', type: 'string',},
  rewardDate: {title: '获得奖励日期',order: 2,view: 'datetime', type: 'string',},
  rewardPoint: {title: '奖励所需分数',order: 3,view: 'number', type: 'number',},
  rewardName: {title: '奖励项名称',order: 4,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}